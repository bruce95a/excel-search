package com.github.bruce95a.excel.search.service.impl;

import com.github.bruce95a.excel.search.entity.PageItems;
import com.github.bruce95a.excel.search.repository.ItemRepo;
import com.github.bruce95a.excel.search.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Override
    public PageItems find(String keyword, Integer index) {
        PageItems pageItems = new PageItems();

        pageItems.setItems(itemRepo.findAll());
        pageItems.setTotal(5);
        pageItems.setSize(1);
        pageItems.setPage(3);
        return pageItems;
    }

    @Override
    public void reload() {

    }
}
