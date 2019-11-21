package com.shopping.service;

import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Intergral;
import com.shopping.entity.WholeRetail;

public interface WholeRetailService {
    WholeRetail findWholeRetail() throws SuperMarketException;

    void modifyWholeRetail(WholeRetail wholeRetail);

    Integer findIntegral();

    void modifyIntegral(Intergral intergral);
}
