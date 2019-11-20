package com.shopping.entity;

import lombok.Data;

@Data
public class SlideShow {
    private Integer id;

    private String viewurl;

    private Long linktype;

    private String linkurl;

    private String linkid;

    private Integer sort;
}