package com.github.bruce95a.excel.search.entity;

import java.io.Serializable;
import java.util.List;

public class PageItems implements Serializable {
    private Integer page; // current page index
    private Integer size; // page size
    private Integer total; // total page
    private Integer totalNm; // total num
    private List<Item> items;
    private List<HealInd> healInds;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalNm() {
        return totalNm;
    }

    public void setTotalNm(Integer totalNm) {
        this.totalNm = totalNm;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<HealInd> getHealInds() {
        return healInds;
    }

    public void setHealInds(List<HealInd> healInds) {
        this.healInds = healInds;
    }
}
