package com.shopping.entity;

import lombok.Data;

import java.util.Date;

@Data
public class VolumeAndMoney {

    private Date click_date;//时间

    private Integer num;//销量

    private Double money;//销售额
}
