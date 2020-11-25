package com.github.bruce95a.excel.search.entity;

import java.io.Serializable;

public class HealInd implements Serializable {
    private String healCd;
    private String healNm;
    private String HealDesc;
    private String industryCd;

    public String getHealCd() {
        return healCd;
    }

    public void setHealCd(String healCd) {
        this.healCd = healCd;
    }

    public String getHealNm() {
        return healNm;
    }

    public void setHealNm(String healNm) {
        this.healNm = healNm;
    }

    public String getHealDesc() {
        return HealDesc;
    }

    public void setHealDesc(String healDesc) {
        HealDesc = healDesc;
    }

    public String getIndustryCd() {
        return industryCd;
    }

    public void setIndustryCd(String industryCd) {
        this.industryCd = industryCd;
    }
}
