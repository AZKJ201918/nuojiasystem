package com.shopping.service;

import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.DeatilBanner;

import java.util.List;

public interface DetailService {
    List<DeatilBanner> findDetailBanner(Integer id) throws SuperMarketException;

    void modifyDetailBanner(DeatilBanner deatilBanner);

    void removeDetailBanner(Integer id);

    void addDetailBanner(DeatilBanner deatilBanner);
}
