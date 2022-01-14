package com.trashparadise.lifemanagerserver.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SessionMapper {
    @Insert("INSERT INTO session (uuid,session) values (#{uuid},#{session})")
    int insert(String uuid, String session);

    @Select("SELECT COUNT(*) FROM session WHERE uuid=#{uuid} AND session=#{session}")
    int select(String uuid,String session);

}

