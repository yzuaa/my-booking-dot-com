package com.laioffer.staybooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "stay")
@JsonDeserialize(builder = Stay.Builder.class)
public class Stay implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String address;
    @JsonProperty("guest_number")
    private int guestNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")//FK改名
    private User host;

    @JsonIgnore//mappedBy意思是在Many的那张表里添加一个FK column对应一的那张表，记录两表的关联关系;cascade是级联关系，LAZY指要用的时候再去读
    //不加mappedBy会创建出一个额外的表记录两表关系，ManyToOne没有mappedBy
    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<StayReservedDate> reservedDates;

    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, fetch=FetchType.EAGER)//搜该stay时就需要返回图片
    private List<StayImage> images;


    public Stay() {}//数据库读出来的数据转化成java的一个object需要call这个空的constructor

    private Stay(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.address = builder.address;
        this.guestNumber = builder.guestNumber;
        this.host = builder.host;
        this.reservedDates = builder.reservedDates;
        this.images = builder.images;
    }

    public List<StayImage> getImages() {
        return images;
    }

    public Stay setImages(List<StayImage> images) {
        this.images = images;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public User getHost() {
        return host;
    }

    public List<StayReservedDate> getReservedDates() {
        return reservedDates;
    }

    public static class Builder {//为了把从json读出来的数据转化成一个obj

        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("address")
        private String address;

        @JsonProperty("guest_number")
        private int guestNumber;

        @JsonProperty("host")
        private User host;

        @JsonProperty("dates")
        private List<StayReservedDate> reservedDates;

        @JsonProperty("images")
        private List<StayImage> images;


        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setGuestNumber(int guestNumber) {
            this.guestNumber = guestNumber;
            return this;
        }

        public Builder setHost(User host) {
            this.host = host;
            return this;
        }

        public Builder setReservedDates(List<StayReservedDate> reservedDates) {
            this.reservedDates = reservedDates;
            return this;
        }

        public Builder setImages(List<StayImage> images) {
            this.images = images;
            return this;
        }

        public Stay build() {
            return new Stay(this);
        }
    }
}
