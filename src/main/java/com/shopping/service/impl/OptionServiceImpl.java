package com.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Commodity;
import com.shopping.entity.Link;
import com.shopping.entity.Options;
import com.shopping.mapper.OptionsMapper;
import com.shopping.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OptionServiceImpl implements OptionService {
    @Autowired
    private OptionsMapper optionsMapper;
    @Override
    public List<Options> findAllOption() throws SuperMarketException {
        List<Options> optionsList = optionsMapper.selectAllOption();
        if (optionsList==null){
            throw new SuperMarketException("没有按钮信息");
        }
        return optionsList;
    }

    @Override
    public void addOption(Options options) {
        optionsMapper.insertSelective(options);
    }

    @Override
    public void modifyOption(Options options) {
        optionsMapper.updateByPrimaryKeySelective(options);
    }

    @Override
    public void removeOption(Integer id) {
        optionsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Link> findAllLink() {
        return optionsMapper.selectAllLink();
    }

    @Override
    public PageInfo findLikeCommodity(String name, Integer page, Integer limit) throws SuperMarketException {
        PageHelper.startPage(page,limit);
        List<Commodity> commodityList = optionsMapper.selectLikeCommodity(name);
        if (commodityList==null){
            throw new SuperMarketException("没有商品信息");
        }
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        return pageInfo;
    }
}
