package com.shopping.service.impl;

import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Intergral;
import com.shopping.entity.WholeRetail;
import com.shopping.mapper.IntergralMapper;
import com.shopping.mapper.WholeRetailMapper;
import com.shopping.service.WholeRetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WholeRetailServiceImpl implements WholeRetailService {
    @Autowired
    private WholeRetailMapper wholeRetailMapper;
    @Autowired
    private IntergralMapper intergralMapper;
    @Override
    public WholeRetail findWholeRetail() throws SuperMarketException {
        WholeRetail wholeRetail = wholeRetailMapper.selectWholeRetail();
        if (wholeRetail==null){
            throw new SuperMarketException("没有全局分销信息");
        }
        return wholeRetail;
    }

    @Override
    public void modifyWholeRetail(WholeRetail wholeRetail) {
        wholeRetailMapper.updateByPrimaryKeySelective(wholeRetail);
    }

    @Override
    public Integer findIntegral() {
        return wholeRetailMapper.selectIntegral();
    }

    @Override
    public void modifyIntegral(Intergral intergral) {
        intergralMapper.updateByPrimaryKeySelective(intergral);
    }
}
