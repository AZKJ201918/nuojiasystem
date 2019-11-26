package com.shopping.service.impl;

import com.shopping.commons.exception.SuperMarketException;
import com.shopping.mapper.CensusMapper;
import com.shopping.service.CensusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CensusServiceImpl implements CensusService {
    @Autowired
    private CensusMapper censusMapper;
    @Override
    public List<Map<Object, Object>> findVolumeAndMoney(Integer id) throws SuperMarketException {
        List<Map<Object,Object>> volumeAndMoneyList=null;
        if (id==null){
            volumeAndMoneyList=censusMapper.selectVolumeAndMoney();
        }else {
            volumeAndMoneyList=censusMapper.selectVolumnAndMoneyWithCid(id);
        }
        if (volumeAndMoneyList==null){
            throw new SuperMarketException("没有找到近七天销量和销售额相关信息");
        }
        return volumeAndMoneyList;
    }

    @Override
    public List<Map<String, Object>> findVisitAndRegister() {
        List<Map<String, Object>> mapList = censusMapper.selectVisitAndRegister();
        return mapList;
    }
}
