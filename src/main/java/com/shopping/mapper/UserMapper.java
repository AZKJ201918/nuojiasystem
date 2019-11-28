package com.shopping.mapper;

import com.shopping.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    List<User> selectUser(String name);
    @Options(useGeneratedKeys = true,keyProperty = "uid",keyColumn = "uid")
    @Insert("insert into user (name,password) values (#{name},#{password})")
    int insertUser(User user);
    @Insert("insert into user_role (uid,rid) values (#{uid},#{rid})")
    int insertUser_role(User user);

    int updateUser(User user);
    @Update("update user_role set rid=#{rid} where uid=#{uid}")
    int updateUser_role(User user);
    @Delete("delete from user where uid=#{uid}")
    int deleteUser(User user);
    @Delete("delete from user_role where id=#{id}")
    int deleteUser_role(User user);
    @Select("select count(*) from user where name=#{name}")
    Integer selectUserExists(String name);
    @Select("select uid,name,password from user where name=#{username}")
    User selectName(String username);
    @Select("select r.remark from user u inner join user_role ur on u.uid=ur.uid inner join role r on ur.rid=r.rid where u.name=#{name} and u.password=#{password}")
    List<String> selectRoles(User user);
    @Select("select rid,rname,remark from role")
    List<User> SelectAllRoles();
    @Select("select count(*) from user where name=#{name} and uid!=#{uid}")
    Integer selectUserExsits1(@Param("name") String name,@Param("uid") Integer uid);
    @Select("select rid from user_role where uid=#{uid}")
    Integer selectMyRid(Integer uid);
}
