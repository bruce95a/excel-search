package com.github.bruce95a.excel.search.controller;

import com.github.bruce95a.excel.search.entity.PageItems;
import com.github.bruce95a.excel.search.service.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private IItemService itemService;

    @GetMapping("/items")
    public PageItems getItems(@RequestParam(value = "k", defaultValue = "") String keyword,
                              @RequestParam(value = "i", defaultValue = "1") Integer index) {
        logger.debug("keyword", keyword);
        logger.debug("pageIndex", index);
        return itemService.find(keyword, index);
    }

    @GetMapping("/reload")
    public PageItems reload() {
        itemService.reload();
        return itemService.find("", 1);
    }
}
