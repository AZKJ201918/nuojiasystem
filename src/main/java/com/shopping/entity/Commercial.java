package com.shopping.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class Commercial {
    private Integer id;

    private Integer cid;

    private BigDecimal subtract;

    private Integer fulld;

    private BigDecimal fulldiscount;

    private Integer postage;

    private Integer fulls;

    private Integer fullsubtract;

    private BigDecimal discount;

    private String aid;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date starttime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GTM+8")
    private Date endtime;

    private Integer status;

    private List<Activity> activityList;

}