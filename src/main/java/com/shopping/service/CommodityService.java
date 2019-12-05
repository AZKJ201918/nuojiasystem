
package com.shopping.service;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Commodity;

public interface CommodityService {
    PageInfo<Commodity> findCommodityAttribute(String name, Integer page, Integer limit) throws SuperMarketException;

    void modifyCommodityAttribute(Commodity commodity);

    void addCommodityAttribute(Commodity commodity);

    void removeCommodityAttribute(Integer id);
}

