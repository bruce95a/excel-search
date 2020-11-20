package com.github.bruce95a.excel.search.service.impl;

import com.github.bruce95a.excel.search.entity.Item;
import com.github.bruce95a.excel.search.entity.PageItems;
import com.github.bruce95a.excel.search.repository.ItemRepo;
import com.github.bruce95a.excel.search.service.IItemService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
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

    @Value("${excel.file.path}")
    private String filePath;

    @Override
    public PageItems find(String keyword, int page, int size) {
        int index = (page - 1) * size;
        int count = 0;
        PageItems pageItems = new PageItems();
        if ("".equals(keyword)) {
            pageItems.setItems(itemRepo.findAll(index, size));
            count = itemRepo.findAllCount();
        } else {
            pageItems.setItems(itemRepo.findByKeyword(keyword, index, size));
            count = itemRepo.findCountByKeyword(keyword);
        }
        int totalPage = count % size == 0 ? count / size : (count / size + 1);
        pageItems.setTotal(totalPage);
        pageItems.setPage(page);
        pageItems.setSize(size);
        pageItems.setTotalNm(count);
        return pageItems;
    }

    @Override
    public void reload() {
        try {
            List<Item> items = new ArrayList<Item>();
            InputStream is = new FileInputStream(filePath);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            XSSFRow titleCell = xssfSheet.getRow(0);
            for (int i = 2; i <= xssfSheet.getLastRowNum(); i++) {
                XSSFRow xssfRow = xssfSheet.getRow(i);
                int minCell = xssfRow.getFirstCellNum();
                int maxCell = xssfRow.getLastCellNum();
                XSSFCell batch = xssfRow.getCell(0);
                XSSFCell industry = xssfRow.getCell(1);
                XSSFCell category = xssfRow.getCell(2);
                XSSFCell health = xssfRow.getCell(3);
                XSSFCell code = xssfRow.getCell(4);
                XSSFCell name = xssfRow.getCell(5);
                XSSFCell scope = xssfRow.getCell(6);
               // XSSFCell logout = xssfRow.getCell(7);
                //XSSFCell status = xssfRow.getCell(8);
                Item item = new Item();
                item.setBatch(getValue(batch));
                item.setIndustry(getValue(industry));
                item.setCategory(getValue(category));
                item.setHealth(getValue(health));
                item.setCode(getValue(code));
                item.setName(getValue(name));
                item.setScope(getValue(scope));
               // item.setLogout(getValue(logout));
                //item.setStatus(getValue(status));
                items.add(item);
            }
            //全部删除
            itemRepo.deleteAll();
            //批量导入
            itemRepo.insertAll(items);
        } catch (Exception e) {
            logger.error("EXCEL数据导入错误", e);
        }
    }

    //格式方法 获取单元格数据
    private String getValue(XSSFCell xssfRow) {
        if (xssfRow != null) {
            if (xssfRow.getCellType() == CellType.BOOLEAN) {
                return String.valueOf(xssfRow.getBooleanCellValue());
            } else if (xssfRow.getCellType() == CellType.NUMERIC) {
                String result = "";
                if (xssfRow.getCellStyle().getDataFormat() == 22) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    double value = xssfRow.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = xssfRow.getNumericCellValue();
                    CellStyle style = xssfRow.getCellStyle();
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
                return String.valueOf(xssfRow.getStringCellValue());
            }
        } else
            return "0";
    }
}
