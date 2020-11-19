package com.github.bruce95a.excel.search.repository;

import com.github.bruce95a.excel.search.entity.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemRepo {
    @Select("select * from item limit #{size} offset #{index}")
    List<Item> findAll(int index, int size);

    @Select("select count(*) from item")
    Integer findAllCount();

    @Select("select * from item where name like '%' || #{keyword} || '%' limit #{size} offset #{index}")
    List<Item> findByKeyword(String keyword, int index, int size);

    @Select("select count(*) from item where name like '%' || #{keyword} || '%'")
    Integer findCountByKeyword(String keyword);

    Integer insertAll(List<Item> items);

    @Delete("delete from item")
    Integer deleteAll();
}
