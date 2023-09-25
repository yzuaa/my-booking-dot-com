package com.laioffer.staybooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stay_image")
public class StayImage implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    private String url;

    @ManyToOne
    @JoinColumn(name = "stay_id")//定义FK显示的名字
    @JsonIgnore //不需要返回该info 在多对一和一对多关系中肯定有一方需要加这个 否则会出现circular dependency
    private Stay stay;

    public StayImage() {}

    public StayImage(String url, Stay stay) {
        this.url = url;
        this.stay = stay;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Stay getStay() {
        return stay;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
    }
}
