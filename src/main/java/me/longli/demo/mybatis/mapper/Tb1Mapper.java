package me.longli.demo.mybatis.mapper;

import me.longli.demo.mybatis.dataobject.Tb1DO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Tb1Mapper {

    @Select("select * from tb1 where id = #{id}")
    Tb1DO getById(int id);
}
