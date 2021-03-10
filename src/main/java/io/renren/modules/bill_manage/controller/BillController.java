package io.renren.modules.bill_manage.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.bill_manage.entity.DayBillEntity;
import io.renren.modules.bill_manage.service.DayBillService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.bill_manage.entity.BillEntity;
import io.renren.modules.bill_manage.service.BillService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-09 16:04:03
 */
@RestController
@RequestMapping("bill_manage/bill")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private DayBillService dayBillService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bill_manage:bill:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = billService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bill_manage:bill:info")
    public R info(@PathVariable("id") Integer id){
		BillEntity bill = billService.getById(id);

        return R.ok().put("bill", bill);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bill_manage:bill:save")
    public R save(@RequestBody BillEntity bill){
		billService.save(bill);

        //在添加一条账目时将其bill_id 判断在日账目中是否存在，如果不存在就添加新的一个日账单中,存在就不做处理
        QueryWrapper<DayBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("day_billid",bill.getBillId());
        DayBillEntity dayBillEntity=dayBillService.getOne(queryWrapper);
        //如果存在
        if(dayBillEntity!=null)
        {
            //拿到日账单实体对其收支进行更新
            if(bill.getBillInout().equals("1")){
                dayBillEntity.setDayIncome(dayBillEntity.getDayIncome()+bill.getBillAccount());
            }
            else dayBillEntity.setDayOutcome(dayBillEntity.getDayOutcome()+bill.getBillAccount());
            dayBillService.updateById(dayBillEntity);
        }
        //如果不存在
        else {
            DayBillEntity dayBillEntity1 = new DayBillEntity();
            dayBillEntity1.setDayBillid(bill.getBillId());
            System.out.println();
            if(bill.getBillInout().equals("1")){
                dayBillEntity1.setDayIncome((float) 0+bill.getBillAccount());
                dayBillEntity1.setDayOutcome((float) 0);
            }
            else {
                dayBillEntity1.setDayIncome((float) 0);
                dayBillEntity1.setDayOutcome((float) 0 + bill.getBillAccount());
            }
            dayBillEntity1.setDayYear(bill.getBillId() / 10000);
            dayBillEntity1.setDayMon(bill.getBillId() % 10000 / 100);
            dayBillEntity1.setDayDay(bill.getBillId() / 1000000);
            //求星期数
            String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
            int y=dayBillEntity1.getDayYear()%100;
            int c=dayBillEntity1.getDayYear()/100;
            int m=dayBillEntity1.getDayMon();
            int d=dayBillEntity1.getDayDay();
            if(m==1||m==2){
                y--;
                m+=12;
            }
            int w = y + y / 4 + c / 4 - 2 * c + 13 * (m + 1) / 5 + d - 1;//蔡勒公式的公式
            while (w < 0) w += 7;//确保余数为正
            w %= 7;
            System.out.println(w);
            dayBillEntity1.setDayWeek(weeks[w]);
            dayBillService.save(dayBillEntity1);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bill_manage:bill:update")
    public R update(@RequestBody BillEntity bill){
		billService.updateById(bill);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bill_manage:bill:delete")
    public R delete(@RequestBody Integer[] ids){
		billService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
