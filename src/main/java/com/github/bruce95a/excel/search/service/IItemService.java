package com.github.bruce95a.excel.search.service;

import com.github.bruce95a.excel.search.entity.PageItems;

public interface IItemService {
    PageItems find(String keyword, Integer index);
    void reload();
}
