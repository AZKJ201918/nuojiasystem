package com.shopping.service;

import com.shopping.commons.exception.SuperMarketException;

import java.util.List;
import java.util.Map;

public interface CensusService {
    List<Map<Object, Object>> findVolumeAndMoney(Integer id) throws SuperMarketException;

    List<Map<String,Object>> findVisitAndRegister();
}
