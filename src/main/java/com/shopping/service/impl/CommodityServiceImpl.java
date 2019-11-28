package com.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.*;
import com.shopping.mapper.*;
import com.shopping.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    @Autowired
    private DeatilBannerMapper deatilBannerMapper;
    @Override
    public PageInfo<Commodity> findCommodityAttribute(String name, Integer page, Integer limit) throws SuperMarketException {
        PageHelper.startPage(page,limit);
        List<Commodity> commodityList = commodityMapper.selectCommodity(name);
        HashOperations hos = redisTemplate.opsForHash();
        if (commodityList==null){
            throw new SuperMarketException("没有商品");
        }
        for (Commodity commodity:commodityList){
            Integer id = commodity.getId();
            Integer isintegral = commodity.getIsintegral();
            Integer retail = commodity.getRetail();
            //商品流水查出来
            //Volumn volumn=volumnMapper.selectVolumnByCid(id);
            //commodity.setVolumn(volumn);
            Commercial commercial = commercialMapper.selectByCid(id);
            if (commercial!=null) {
                String aid = commercial.getAid();
                System.out.println(aid);
                if (aid != null && !aid.equals("")) {
                    List<Activity> activityList = activityMapper.selectActivityByAid(aid);
                    System.out.println(activityList);
                    commercial.setActivityList(activityList);
                }
                commodity.setCommercial(commercial);
            }
            Integer repertory = (Integer) hos.get("repertory:"+id + "", "repertory");
            if (repertory==null){
                Integer repertory1 = commodity.getRepertory();
                String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                Integer volumn=commodityMapper.selectVolumn(id,format);
                if (volumn==null){
                    repertory=repertory1;
                }else {
                    repertory=repertory1-volumn;
                }
            }
            commodity.setRepertory(repertory);
            List<DeatilBanner> deatilBannerList = deatilBannerMapper.selectDetailBannerByCid(id);
            commodity.setDeatilBannerList(deatilBannerList);
            if (isintegral==1){//是积分商品
                IntegralCommodity integralCommodity= integralCommodityMapper.selectByCid(id);
                commodity.setIntegralCommodity(integralCommodity);
            }
            if (retail==1){//是分销商品
                //WholeRetail wholeRetail=wholeRetailMapper.selectByCid(id);
                Retail retailTable=retailMapper.selectByCid(id);
                //commodity.setWholeRetail(wholeRetail);
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
        /*WholeRetail wholeRetail = commodity.getWholeRetail();
        if (wholeRetail!=null){
            Integer wholeRetailId = wholeRetail.getId();
            if (wholeRetailId!=null){
                wholeRetailMapper.updateByPrimaryKeySelective(wholeRetail);
            }else {
                wholeRetailMapper.insertSelective(wholeRetail);
            }
        }*/
        List<DeatilBanner> deatilBannerList = commodity.getDeatilBannerList();
        if (deatilBannerList!=null){
            Integer deatilBannerId=null;
            for (DeatilBanner deatilBanner:deatilBannerList){
                deatilBannerId = deatilBanner.getId();
                if (deatilBannerId!=null){
                    deatilBannerMapper.updateByPrimaryKeySelective(deatilBanner);
                }else {
                    deatilBannerMapper.insertSelective(deatilBanner);
                }
            }
        }
        if (repertory!=null){
            HashOperations hos = redisTemplate.opsForHash();
            Integer repertory1 = (Integer) hos.get("repertory:"+id + "", "repertory");
            if (repertory1==null){
                Integer reper=commodityMapper.selectRepertory(id);
                String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                Integer volumn=commodityMapper.selectVolumn(id,format);
                if (volumn==null){
                    repertory1=reper;
                }else {
                    repertory1=reper-volumn;
                }
            }
            repertory1+=repertory;
            hos.put("repertory:"+id+"","repertory",repertory1);
        }
    }

    @Override
    public void addCommodityAttribute(Commodity commodity) {
        commodity.setCreatetime(new Date());
        commodityMapper.insertSelective(commodity);
        System.out.println("返回的主键是"+commodity.getId());
        Integer repertory = commodity.getRepertory();
        Commercial commercial = commodity.getCommercial();
        commercial.setCid(commodity.getId());
        if (commercial!=null){
            commercialMapper.insertSelective(commercial);
        }
        IntegralCommodity integralCommodity = commodity.getIntegralCommodity();
        integralCommodity.setCid(commodity.getId());
        if (integralCommodity!=null){
            integralCommodityMapper.insertSelective(integralCommodity);
        }
        Retail retailTable = commodity.getRetailTable();
        retailTable.setCid(commodity.getId());
        if (retailTable!=null){
            retailMapper.insertSelective(retailTable);
        }
        /*WholeRetail wholeRetail = commodity.getWholeRetail();
        if (wholeRetail!=null){
            wholeRetailMapper.insertSelective(wholeRetail);
        }*/
        List<DeatilBanner> deatilBannerList = commodity.getDeatilBannerList();
        for (DeatilBanner deatilBanner:deatilBannerList){
            deatilBanner.setCid(commodity.getId());
            if (deatilBanner!=null){
                deatilBannerMapper.insertSelective(deatilBanner);
            }
        }
        Integer id = commodity.getId();
        if (repertory!=null){//库存上传
            HashOperations hos = redisTemplate.opsForHash();
            hos.put("repertory:"+id+"","repertory",repertory);
        }
    }

    @Override
    public void removeCommodityAttribute(Integer id) {
        commodityMapper.deleteByPrimaryKey(id);
        commercialMapper.deleteByCid(id);
        integralCommodityMapper.deleteByCid(id);
        retailMapper.deleteByCid(id);
        deatilBannerMapper.deleteByCid(id);
        //wholeRetailMapper.deleteByCid(id);
        HashOperations hos = redisTemplate.opsForHash();
        hos.delete("repertory:"+id+"","repertory");
    }
}
