package com.shopping.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class WholeRetail {
    private Integer id;

    private BigDecimal wholeparent;

    private BigDecimal wholegrand;

    private Integer parenttype;

    private Integer grandtype;

    private Integer cid;


}