package com.shopping.service;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Link;
import com.shopping.entity.Options;

import java.util.List;

public interface OptionService {
    List<Options> findAllOption() throws SuperMarketException;

    void addOption(Options options);

    void modifyOption(Options options);

    void removeOption(Integer id);

    List<Link> findAllLink();

    PageInfo findLikeCommodity(String name, Integer page, Integer limit) throws SuperMarketException;
}
