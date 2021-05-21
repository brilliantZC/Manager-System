package io.renren.modules.goodsorder_manage.excel;

import io.renren.modules.bill_manage.entity.DayBillEntity;
import io.renren.modules.bill_manage.service.DayBillService;
import io.renren.modules.goods_manage.service.GoodsService;
import io.renren.modules.goodsorder_manage.entity.GoodsorderEntity;
import io.renren.modules.goodsorder_manage.service.GoodsorderService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/goodsorder_manage")
public class OrderExcel {

    @Autowired
    private GoodsorderService goodsorderService;

    @RequestMapping(value = "/excel/order",method = RequestMethod.POST)
    @RequiresPermissions("goodsorder_manage:excel")
    public void excel(HttpServletResponse response)
    {
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        List<GoodsorderEntity> goodsorderEntities = goodsorderService.selectAll();
        HSSFWorkbook workbook = new HSSFWorkbook(); //1.在内存中创建一个excel文件
        HSSFSheet sheet = workbook.createSheet("商家历史订单");//单位部门信息sheet
        String fileName = "完成订单"  + ".xls";//设置要导出的文件的名字
        String[] headers = { "买家姓名", "电话","地址", "选购商品","价格","订单创建时间","订单持续时间","订单状态"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //在表中存放查询到的数据放入对应的列
        for (GoodsorderEntity e : goodsorderEntities) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(e.getName());
            row1.createCell(1).setCellValue(e.getPhone());
            row1.createCell(2).setCellValue(e.getAddress());
            row1.createCell(3).setCellValue(e.getGoods());
            row1.createCell(4).setCellValue(e.getPrice());
            row1.createCell(5).setCellValue(e.getTimeStar());
            row1.createCell(6).setCellValue(e.getTimeStay());
            row1.createCell(7).setCellValue(e.getZztmc());
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
