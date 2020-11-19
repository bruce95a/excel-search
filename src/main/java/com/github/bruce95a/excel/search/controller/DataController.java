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

    private final int size = 10;

    @GetMapping("/items")
    public PageItems getItems(@RequestParam(value = "q", defaultValue = "") String keyword,
                              @RequestParam(value = "p", defaultValue = "1") Integer page) {
        logger.debug("keyword", keyword);
        logger.debug("pageIndex", page);
        return itemService.find(keyword, page, size);
    }

    @GetMapping("/reload")
    public PageItems reload() {
        itemService.reload();
        return itemService.find("", 1, size);
    }
}
