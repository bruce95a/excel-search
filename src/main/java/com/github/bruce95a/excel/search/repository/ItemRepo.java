package com.github.bruce95a.excel.search.repository;

import com.github.bruce95a.excel.search.entity.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemRepo {
    @Select("select * from item")
    List<Item> findAll();

    Integer insertAll(List infos);

    @Delete("delete from item")
    Integer deleteAll();
}
