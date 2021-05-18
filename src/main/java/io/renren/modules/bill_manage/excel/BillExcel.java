package io.renren.modules.bill_manage.excel;

import io.renren.modules.bill_manage.entity.DayBillEntity;
import io.renren.modules.bill_manage.entity.MonBillEntity;
import io.renren.modules.bill_manage.service.DayBillService;
import io.renren.modules.bill_manage.service.MonBillService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.poi.hssf.usermodel.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/bill_manage")
public class BillExcel {

    @Autowired
    private DayBillService dayBillService;
    @Autowired
    private MonBillService monBillService;

    @RequestMapping(value = "/excel/daybill",method = RequestMethod.POST)
    @RequiresPermissions("bill_manage:excel")
    public void excel(HttpServletResponse response)
    {
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        List<DayBillEntity> daybill = dayBillService.selectAll();
        HSSFWorkbook workbook = new HSSFWorkbook(); //1.在内存中创建一个excel文件
        HSSFSheet sheet = workbook.createSheet("日收支详情");//单位部门信息sheet
        String fileName = "日收支"  + ".xls";//设置要导出的文件的名字
        String[] headers = { "日期", "星期","收入", "支出","纯收入"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //在表中存放查询到的数据放入对应的列
        for (DayBillEntity e : daybill) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(e.getDayBillid());
            row1.createCell(1).setCellValue(e.getDayWeek());
            row1.createCell(2).setCellValue(e.getDayIncome());
            row1.createCell(3).setCellValue(e.getDayOutcome());
            row1.createCell(4).setCellValue(e.getDayPure());
            rowNum++;
        }
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode( fileName,"iso-8859-1"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/excel/monbill",method = RequestMethod.POST)
    @RequiresPermissions("bill_manage:excel")
    public void excelmon(HttpServletResponse response)
    {
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        List<MonBillEntity> monbill = monBillService.selectAll();
        HSSFWorkbook workbook = new HSSFWorkbook(); //1.在内存中创建一个excel文件
        HSSFSheet sheet = workbook.createSheet("日收支详情");//单位部门信息sheet
        String fileName = "月收支"  + ".xls";//设置要导出的文件的名字
        String[] headers = { "年份", "月份","月收入", "月支出","月纯盈利"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            System.out.println("走过此方法！！！！！！！！！！");
        }
        //在表中存放查询到的数据放入对应的列
        for (MonBillEntity e : monbill) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(e.getMonYear());
            row1.createCell(1).setCellValue(e.getMonMon());
            row1.createCell(2).setCellValue(e.getMonIncome());
            row1.createCell(3).setCellValue(e.getMonOutcome());
            row1.createCell(4).setCellValue(e.getMonPure());
            rowNum++;
        }
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode( fileName,"iso-8859-1"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

