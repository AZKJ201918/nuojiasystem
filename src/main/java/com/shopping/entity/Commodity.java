package com.shopping.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Commodity {
    private Integer id;

    private String name;

    private String url;

    private BigDecimal price;

    private Integer sales;

    private Integer repertory;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date updatetime;

    private Integer status;

    private Integer type;

    private String detailurl;

    private String specsurl;

    private String saleurl;

    private String subname;

    private Integer retail;//是否是分销商品

    private Integer isintegral;

    private Integer beretail;//能否成为分销商

    private Commercial commercial;

    private IntegralCommodity integralCommodity;

    private Retail retailTable;//分销表

    private WholeRetail wholeRetail;//全局表

    private Volumn volumn;

    private List<DeatilBanner> deatilBannerList;

    private Double postage;

}