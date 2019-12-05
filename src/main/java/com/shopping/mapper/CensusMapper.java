package com.shopping.mapper;

import com.shopping.entity.Cash;
import com.shopping.entity.VolumeAndMoney;
import com.shopping.entity.WxUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface CensusMapper {
    List<Map<Object,Object>> selectVolumeAndMoney();

    List<Map<Object,Object>> selectVolumnAndMoneyWithCid(Integer id);

    List<Map<String,Object>> selectVisitAndRegister();
    @Select("select uuid,money from wxuser")
    List<WxUser> selectPlease();
    @Select("select phone,name from register where uuid=#{uuid}")
    String selectPhone(String uuid);
    @Select("select bankid from bank where rid=#{uuid}")
    String selectBankId(String uuid);
    @Select("update wxuser set retailmoney=retailmoney-money,money=0 where uuid=#{uuid}")
    int updateCash(String uuid);
    @Update("update cash set status=1 where uuid=#{uuid} and status=0")
    int updateCashWater(String uuid);
    @Select("select cash,status,createtime from cash where uuid=#{uuid}")
    List<Cash> selectCash();
    @Select("SELECT c.cash,c.status,c.createtime,r.phone,r.name,b.bankid FROM cash c  INNER JOIN register r ON c.uuid=r.uuid INNER JOIN bank b ON r.uuid=b.rid WHERE b.status=0")
    List<Map<String,Object>> selectCashBank();
}
