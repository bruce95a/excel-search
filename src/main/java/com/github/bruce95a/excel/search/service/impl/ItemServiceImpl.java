package com.github.bruce95a.excel.search.service.impl;

import com.github.bruce95a.excel.search.entity.Item;
import com.github.bruce95a.excel.search.entity.PageItems;
import com.github.bruce95a.excel.search.entity.Param;
import com.github.bruce95a.excel.search.repository.HealIndRepo;
import com.github.bruce95a.excel.search.repository.ItemRepo;
import com.github.bruce95a.excel.search.service.IItemService;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ItemServiceImpl implements IItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private HealIndRepo healIndRepo;

    @Value("${excel.file.path}")
    private String filePath;

    @Override
    public PageItems find(String keyword, int page, int size, String tpCd) {
        //tpCd 操作类型 1-查询名录 2-查询健康产业代码
        int index = (page - 1) * size;
        int count = 0;
        PageItems pageItems = new PageItems();
        Param param = new Param();
        param.setIndex(index);
        param.setKeyword(keyword);
        param.setSize(size);
        if("1".equals(tpCd)){
            pageItems.setItems(itemRepo.findAll(param));
            count = itemRepo.findAllCount(keyword);
        }else if("2".equals(tpCd)){
            pageItems.setHealInds(healIndRepo.findAll(param));
            count = healIndRepo.findAllCount(keyword);
        }
        int totalPage = count % size == 0 ? count / size : (count / size + 1);
        totalPage = totalPage == 0 ? 1 : totalPage;
        pageItems.setTotal(totalPage);
        pageItems.setPage(page);
        pageItems.setSize(size);
        pageItems.setTotalNm(count);
        return pageItems;
    }

    @Override
    public void reload() {
        InputStream is = null;
        try {
            List<Item> items = new ArrayList<>();
            is = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Item item = new Item();
                item.setBatch(getValue(row.getCell(0)));
                item.setIndustry(getValue(row.getCell(1)));
                item.setCategory(getValue(row.getCell(2)));
                item.setHealth(getValue(row.getCell(3)));
                item.setCode(getValue(row.getCell(4)));
                item.setName(getValue(row.getCell(5)));
                item.setScope(getValue(row.getCell(6)));
                items.add(item);
            }
            //全部删除
            itemRepo.deleteAll();
            //批量导入
            itemRepo.insertAll(items);
        } catch (Exception e) {
            logger.error("EXCEL数据导入错误", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("EXCEL数据导入错误", e);
                }
            }
        }
    }

    //格式方法 获取单元格数据
    private String getValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.BOOLEAN) {
                return String.valueOf(cell.getBooleanCellValue());
            } else if (cell.getCellType() == CellType.NUMERIC) {
                String result = "";
                if (cell.getCellStyle().getDataFormat() == 22) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("#");
                    }
                    result = format.format(value);
                }
                return result;
            } else {
                return String.valueOf(cell.getStringCellValue());
            }
        } else
            return "0";
    }
}
