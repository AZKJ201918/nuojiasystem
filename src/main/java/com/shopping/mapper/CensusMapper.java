package com.shopping.mapper;

import com.shopping.entity.VolumeAndMoney;

import java.util.List;
import java.util.Map;

public interface CensusMapper {
    List<Map<Object,Object>> selectVolumeAndMoney();

    List<Map<Object,Object>> selectVolumnAndMoneyWithCid(Integer id);

    List<Map<String,Object>> selectVisitAndRegister();
}
