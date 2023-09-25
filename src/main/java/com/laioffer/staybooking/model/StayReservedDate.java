package com.laioffer.staybooking.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stay_reserved_date")
public class StayReservedDate implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StayReservedDateKey id; //不是id,是以StayReservedDateKey里的fields创建出column,date和stay_id，这俩组合在一起是PK

    @ManyToOne
    @MapsId("stay_id")//这个column既是组合键的一部分 又是FK 这个@可以不再单独创建出一个FK column，比较重复
    private Stay stay;

    public StayReservedDate() {}

    public StayReservedDate(StayReservedDateKey id, Stay stay) {
        this.id = id;
        this.stay = stay;
    }

    public StayReservedDateKey getId() {
        return id;
    }

    public Stay getStay() {
        return stay;
    }

}
