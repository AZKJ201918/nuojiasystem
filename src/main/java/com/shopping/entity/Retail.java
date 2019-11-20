package com.shopping.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Retail {
    private Integer id;

    private BigDecimal parent;

    private BigDecimal grand;

    private Integer parenttype;

    private Integer grandtype;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date outtime;

    private Integer cid;

}