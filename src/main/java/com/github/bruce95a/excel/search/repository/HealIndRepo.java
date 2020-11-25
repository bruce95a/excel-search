package com.github.bruce95a.excel.search.repository;

import com.github.bruce95a.excel.search.entity.HealInd;
import com.github.bruce95a.excel.search.entity.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HealIndRepo {
    List<HealInd> findAll(Param param);

    Integer findAllCount(String keyword);
}
