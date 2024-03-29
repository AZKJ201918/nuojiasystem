package com.shopping.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class Orders {
    private Integer id;

    private String uid;

    private String orderid;

    private BigDecimal price;

    private BigDecimal finalprice;

    private Integer addressid;

    private String cid;

    private Integer status;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date closetime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date paytime;

    private String courier;

    private String company;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date sendtime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date recievetime;

    private Address address;

    private List<Map<String,Object>> cidAndNum;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date starttime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date endtime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date sendouttime;
}