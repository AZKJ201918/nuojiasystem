package com.shopping.entity;

import lombok.Data;

@Data
public class PrintOrder {

    private String orderid;

    private Double finalprice;

    private Integer num;

    private String cname;

    private String subname;

    private String name;

    private String phone;

    private String city;

    private String province;

    private String area;

    private String detail;
}
