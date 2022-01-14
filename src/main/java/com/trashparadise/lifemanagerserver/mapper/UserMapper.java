package com.trashparadise.lifemanagerserver.mapper;

import com.trashparadise.lifemanagerserver.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user (username,uuid,password) values (#{username},#{uuid},#{password})")
    int insert(User user);

    @Select("SELECT uuid FROM user WHERE username=#{username}")
    ArrayList<String> select(String username);

    @Select("SELECT COUNT(*) FROM user WHERE username=#{username} AND password=#{password}")
    int login(String username, String password);
}

