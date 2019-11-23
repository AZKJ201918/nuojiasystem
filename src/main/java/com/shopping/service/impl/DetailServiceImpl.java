package com.shopping.service.impl;

import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.DeatilBanner;
import com.shopping.mapper.DeatilBannerMapper;
import com.shopping.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailServiceImpl implements DetailService {
    @Autowired
    private DeatilBannerMapper deatilBannerMapper;
    @Override
    public List<DeatilBanner> findDetailBanner(Integer id) throws SuperMarketException {
        List<DeatilBanner> deatilBannerList = deatilBannerMapper.selectDetailBannerByCid(id);
        if (deatilBannerList==null){
            throw new SuperMarketException("该商品没有轮播图");
        }
        return deatilBannerList;
    }

    @Override
    public void modifyDetailBanner(DeatilBanner deatilBanner) {
        deatilBannerMapper.updateByPrimaryKeySelective(deatilBanner);
    }

    @Override
    public void removeDetailBanner(Integer id) {
        deatilBannerMapper.deleteByCid(id);
    }

    @Override
    public void addDetailBanner(DeatilBanner deatilBanner) {
        deatilBannerMapper.insertSelective(deatilBanner);
    }
}
