package com.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.*;
import com.shopping.mapper.*;
import com.shopping.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommercialMapper commercialMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private IntegralCommodityMapper integralCommodityMapper;
    @Autowired
    private RetailMapper retailMapper;
    @Autowired
    private WholeRetailMapper wholeRetailMapper;
    @Autowired
    private VolumnMapper volumnMapper;
    @Override
    public PageInfo<Commodity> findCommodityAttribute(String name, Integer page, Integer limit) throws SuperMarketException {
        PageHelper.startPage(page,limit);
        List<Commodity> commodityList = commodityMapper.selectCommodity(name);
        ValueOperations ops = redisTemplate.opsForValue();
        if (commodityList==null){
            throw new SuperMarketException("没有商品");
        }
        for (Commodity commodity:commodityList){
            Integer id = commodity.getId();
            Integer isintegral = commodity.getIsintegral();
            Integer retail = commodity.getRetail();
            Commercial commercial = commercialMapper.selectByCid(id);
            if (commercial!=null) {
                String aid = commercial.getAid();
                System.out.println(aid);
                Integer repertory = (Integer) ops.get(id + "");
                commodity.setRepertory(repertory);
                if (aid != null && !aid.equals("")) {
                    List<Activity> activityList = activityMapper.selectActivityByAid(aid);
                    System.out.println(activityList);
                    commercial.setActivityList(activityList);
                }
                commodity.setCommercial(commercial);
            }
            //商品流水查出来
            Volumn volumn=volumnMapper.selectVolumnByCid(id);
            commodity.setVolumn(volumn);
            if (isintegral==1){//是积分商品
                IntegralCommodity integralCommodity= integralCommodityMapper.selectByCid(id);
                commodity.setIntegralCommodity(integralCommodity);
            }
            if (retail==1){//是分销商品
                WholeRetail wholeRetail=wholeRetailMapper.selectByCid(id);
                Retail retailTable=retailMapper.selectByCid(id);
                commodity.setWholeRetail(wholeRetail);
                commodity.setRetailTable(retailTable);
            }
        }
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        return pageInfo;
    }

    @Override
    public void modifyCommodityAttribute(Commodity commodity) {
        Integer repertory = commodity.getRepertory();
        Integer id = commodity.getId();
        ValueOperations ops = redisTemplate.opsForValue();
        if (repertory!=null){
            Integer reper = (Integer) ops.get(id + "");
            reper+=repertory;
            ops.set(id+"",reper);
        }
        commodity.setUpdatetime(new Date());
        commodityMapper.updateByPrimaryKeySelective(commodity);
        Commercial commercial = commodity.getCommercial();
        if (commercial!=null){
            Integer commercialId = commercial.getId();
            if (commercialId!=null){
                commercialMapper.updateByPrimaryKeySelective(commercial);
            }else {
                commercialMapper.insertSelective(commercial);
            }
        }
        IntegralCommodity integralCommodity = commodity.getIntegralCommodity();
        if (integralCommodity!=null){
            Integer integralCommodityId = integralCommodity.getId();
            if (integralCommodityId!=null){
                integralCommodityMapper.updateByPrimaryKeySelective(integralCommodity);
            }else {
                integralCommodityMapper.insertSelective(integralCommodity);
            }
        }
        Retail retailTable = commodity.getRetailTable();
        if (retailTable!=null){
            Integer retailTableId = retailTable.getId();
            if (retailTableId!=null){
                retailMapper.updateByPrimaryKeySelective(retailTable);
            }else {
                retailMapper.insertSelective(retailTable);
            }
        }
        WholeRetail wholeRetail = commodity.getWholeRetail();
        if (wholeRetail!=null){
            Integer wholeRetailId = wholeRetail.getId();
            if (wholeRetailId!=null){
                wholeRetailMapper.updateByPrimaryKeySelective(wholeRetail);
            }else {
                wholeRetailMapper.insertSelective(wholeRetail);
            }
        }
    }

    @Override
    public void addCommodityAttribute(Commodity commodity) {
        System.out.println("     ");
        ValueOperations ops = redisTemplate.opsForValue();
        commodity.setCreatetime(new Date());
        commodityMapper.insertSelective(commodity);
        ops.set(commodity.getId()+"",commodity.getRepertory());
        Commercial commercial = commodity.getCommercial();
        if (commercial!=null){
            commercialMapper.insertSelective(commercial);
        }
        IntegralCommodity integralCommodity = commodity.getIntegralCommodity();
        if (integralCommodity!=null){
            integralCommodityMapper.insertSelective(integralCommodity);
        }
        Retail retailTable = commodity.getRetailTable();
        if (retailTable!=null){
            retailMapper.insertSelective(retailTable);
        }
        WholeRetail wholeRetail = commodity.getWholeRetail();
        if (wholeRetail!=null){
            wholeRetailMapper.insertSelective(wholeRetail);
        }
    }

    @Override
    public void removeCommodityAttribute(Integer id) {
        commodityMapper.deleteByPrimaryKey(id);
        commercialMapper.deleteByCid(id);
        integralCommodityMapper.deleteByCid(id);
        retailMapper.deleteByCid(id);
        wholeRetailMapper.deleteByCid(id);
    }
}
