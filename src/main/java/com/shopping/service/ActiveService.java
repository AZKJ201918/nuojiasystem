package com.shopping.service;

import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Activity;
import com.shopping.entity.Commercial;

import java.util.List;

public interface ActiveService {
    Commercial findActivity(Integer id) throws SuperMarketException;

    void modifyActivity(Commercial commercial);

    void addActivity(Commercial commercial);

    void removeActivity(Integer id);
   /* List<Activity> findActive() throws SuperMarketException;

    void modifyActive(Activity activity);*/
}
