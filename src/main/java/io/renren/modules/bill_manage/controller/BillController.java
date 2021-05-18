package io.renren.modules.bill_manage.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.bill_manage.entity.DayBillEntity;
import io.renren.modules.bill_manage.entity.MonBillEntity;
import io.renren.modules.bill_manage.service.DayBillService;
import io.renren.modules.bill_manage.service.MonBillService;
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
    @Autowired
    private MonBillService monBillService;

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
            dayBillEntity.setDayPure(dayBillEntity.getDayIncome()-dayBillEntity.getDayOutcome());
            dayBillService.updateById(dayBillEntity);
        }
        //如果不存在
        else {
            DayBillEntity dayBillEntity1 = new DayBillEntity();
            dayBillEntity1.setDayBillid(bill.getBillId());
            if(bill.getBillInout().equals("1")){
                dayBillEntity1.setDayIncome((float) 0+bill.getBillAccount());
                dayBillEntity1.setDayOutcome((float) 0);
            }
            else {
                dayBillEntity1.setDayIncome((float) 0);
                dayBillEntity1.setDayOutcome((float) 0+bill.getBillAccount());
            }
            dayBillEntity1.setDayPure(dayBillEntity1.getDayIncome()-dayBillEntity1.getDayOutcome());
            dayBillEntity1.setDayYear(bill.getBillId() / 10000);
            dayBillEntity1.setDayMon(bill.getBillId() % 10000 / 100);
            dayBillEntity1.setDayDay(bill.getBillId() % 100);
            //求星期数
            String[] weeks = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
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
            dayBillEntity1.setDayWeek(weeks[w]);
            dayBillService.save(dayBillEntity1);
        }

        //月账单处理,同理
       // QueryWrapper<MonBillEntity> monqueryWrapper = new QueryWrapper<>();
       // queryWrapper.eq("mon_mon",bill.getBillId() % 10000 / 100).eq("mon_year",bill.getBillId() / 10000);

        MonBillEntity monBillEntity=monBillService.getOne(new QueryWrapper<MonBillEntity>().eq("mon_mon",bill.getBillId() % 10000 / 100).eq("mon_year",bill.getBillId() / 10000));

        System.out.println(monBillEntity);

        if(monBillEntity!=null){
            if(bill.getBillInout().equals("1")){
                monBillEntity.setMonIncome(monBillEntity.getMonIncome()+bill.getBillAccount());
            }
            else monBillEntity.setMonOutcome(monBillEntity.getMonOutcome()+bill.getBillAccount());
            monBillEntity.setMonPure(monBillEntity.getMonIncome()-monBillEntity.getMonOutcome());
            monBillService.updateById(monBillEntity);
        }
        else {
            MonBillEntity monBillEntity1 = new MonBillEntity();
            monBillEntity1.setMonBillid(bill.getBillId());
            if(bill.getBillInout().equals("1")){
                monBillEntity1.setMonIncome((float) 0+bill.getBillAccount());
                monBillEntity1.setMonOutcome((float) 0);
            }
            else {
                monBillEntity1.setMonIncome((float) 0);
                monBillEntity1.setMonOutcome((float) 0+bill.getBillAccount());
            }
            monBillEntity1.setMonPure(monBillEntity1.getMonIncome()-monBillEntity1.getMonOutcome());
            monBillEntity1.setMonYear(bill.getBillId() / 10000);
            monBillEntity1.setMonMon(bill.getBillId() % 10000 / 100);
            monBillService.save(monBillEntity1);
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
     * 删除  在账单中删除一条账单时应将对应的日月账单金额进行修改
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bill_manage:bill:delete")
    public R delete(@RequestBody Integer[] ids){
        //拿到所有的要删除的实体
        BillEntity[] deleteEntity=new BillEntity[ids.length];
        for(int i=0;i<ids.length;i++){
            deleteEntity[i]= billService.getById(ids[i]);
        }
        //对日月账单的金额进行修改
        for(int i=0;i<ids.length;i++){
            //在删除一条账目时将其bill_id 与日账单中的daybillid对比，相同的进行金额的处理
            QueryWrapper<DayBillEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("day_billid",deleteEntity[i].getBillId());
            DayBillEntity dayBillEntity=dayBillService.getOne(queryWrapper);
            //拿到日账单实体对其收支进行更新
            if(deleteEntity[i].getBillInout().equals("1")){
                dayBillEntity.setDayIncome(dayBillEntity.getDayIncome()-deleteEntity[i].getBillAccount());
            }
            else dayBillEntity.setDayOutcome(dayBillEntity.getDayOutcome()-deleteEntity[i].getBillAccount());
            dayBillEntity.setDayPure(dayBillEntity.getDayIncome()-dayBillEntity.getDayOutcome());
            dayBillService.updateById(dayBillEntity);

            //对月账单进行处理
            QueryWrapper<MonBillEntity> monqueryWrapper = new QueryWrapper<>();
            queryWrapper.eq("mon_mon",deleteEntity[i].getBillId() % 10000 / 100).eq("mon_year",deleteEntity[i].getBillId() / 10000);
            MonBillEntity monBillEntity=monBillService.getOne(monqueryWrapper);
            //拿到月账单实体对其收支进行更新
            if(deleteEntity[i].getBillInout().equals("1")){
                monBillEntity.setMonIncome(monBillEntity.getMonIncome()-deleteEntity[i].getBillAccount());
            }
            else monBillEntity.setMonOutcome(monBillEntity.getMonOutcome()-deleteEntity[i].getBillAccount());
            monBillEntity.setMonPure(monBillEntity.getMonIncome()-monBillEntity.getMonOutcome());
            monBillService.updateById(monBillEntity);
        }
		billService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
