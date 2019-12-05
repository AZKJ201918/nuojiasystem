package com.shopping.entity;

import lombok.Data;

import java.util.List;

@Data
public class WxUser {

    private String uuid;

    private Double money;

    private String bankid;

    private String phone;

    private List<Cash> cashList;
}
