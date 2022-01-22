package com.trashparadise.lifemanagerserver.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface MessageMapper {
    @Insert("INSERT INTO message (src_uuid,dst_uuid,data) values (#{src},#{dst},#{data})")
    int insert(String src, String dst, String data);

//    @Select("SELECT data FROM message WHERE src_uuid=#{src} AND dst_uuid=#{dst}")
//    ArrayList<String> select(String src, String dst);
//
//    @Delete("DELETE FROM message WHERE src_uuid=#{src} AND dst_uuid=#{dst}")
//    int delete(String src, String dst);

    @Select("SELECT data FROM message WHERE dst_uuid=#{src}")
    ArrayList<String> select(String src);

    @Delete("DELETE FROM message WHERE dst_uuid=#{src}")
    int delete(String src);
}
