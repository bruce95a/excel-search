package com.github.bruce95a.excel.search.repository;

import com.github.bruce95a.excel.search.entity.Item;
import com.github.bruce95a.excel.search.entity.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemRepo {

    List<Item> findAll(Param param);

    Integer findAllCount(String keyword);

    Integer insertAll(List<Item> items);

    @Delete("delete from item")
    Integer deleteAll();
}
