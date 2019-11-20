package com.shopping.service.impl;

import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Activity;
import com.shopping.entity.Commercial;
import com.shopping.mapper.ActivityMapper;
import com.shopping.mapper.CommercialMapper;
import com.shopping.service.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActiveServiceImpl implements ActiveService {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private CommercialMapper commercialMapper;

    @Override
    public Commercial findActivity(Integer id) throws SuperMarketException {
        Commercial commercial = commercialMapper.selectByCid(id);
        String aid = commercial.getAid();
        if (commercial==null){
            throw new SuperMarketException("该商品没有活动");
        }
        System.out.println(aid);
        if (aid!=null&&!aid.equals("")){
            List<Activity> activityList= activityMapper.selectActivityByAid(aid);
            System.out.println(activityList);
            commercial.setActivityList(activityList);
        }
        return commercial;
    }

    @Override
    public void modifyActivity(Commercial commercial) {
        commercialMapper.updateByPrimaryKeySelective(commercial);
    }

    @Override
    public void addActivity(Commercial commercial) {
        commercialMapper.insertSelective(commercial);
    }

    @Override
    public void removeActivity(Integer id) {
        commercialMapper.deleteByPrimaryKey(id);
    }
   /* @Override
    public List<Activity> findActive() throws SuperMarketException {
        List<Activity> activityList = activityMapper.selectAllActive();
        if (activityList==null){
            throw new SuperMarketException("没有活动");
        }
        return activityList;
    }

    @Override
    public void modifyActive(Activity activity) {
        activityMapper.updateByPrimaryKeySelective(activity);
    }*/


}
