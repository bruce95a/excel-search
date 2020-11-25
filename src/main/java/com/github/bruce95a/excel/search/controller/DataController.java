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
    public PageItems getItems(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "tpCd", defaultValue = "1") String tpCd) {
        //tpCd 操作类型 1-查询名录 2-查询健康产业代码
        logger.debug("keyword", keyword);
        logger.debug("pageIndex", page);
        logger.debug("tpCd", tpCd);
        return itemService.find(keyword, page, size, tpCd);
    }

    @GetMapping("/reload")
    public PageItems reload() {
        itemService.reload();
        return itemService.find("", 1, size,"1");
    }
}
