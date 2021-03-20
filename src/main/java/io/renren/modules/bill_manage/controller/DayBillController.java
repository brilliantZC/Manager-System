package io.renren.modules.bill_manage.controller;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.bill_manage.entity.CharpieEntity;
import io.renren.modules.bill_manage.service.BillService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.bill_manage.entity.DayBillEntity;
import io.renren.modules.bill_manage.service.DayBillService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-10 10:31:52
 */
@RestController
@RequestMapping("bill_manage/daybill")
public class DayBillController {
    @Autowired
    private DayBillService dayBillService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bill_manage:daybill:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dayBillService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/listday")
    @RequiresPermissions("bill_manage:daybill:listday")
    public R listday(@RequestParam Map<String, Object> params){
        PageUtils page = dayBillService.queryPageday(params);

        return R.ok().put("page", page);
    }

    /**
     * 查出收支用于绘制日账单饼图
     */
    @RequestMapping("/countinout")
    public R countinout(@RequestParam(required=false) String billdate){
        String[] mname={"收入","支出"};
        List<CharpieEntity> mnum=new ArrayList();
        //如果为空就数据库中最后一条数据绘制图表
        if(billdate.isEmpty()){
            DayBillEntity dayBillEntity=dayBillService.getById(dayBillService.count());
            billdate=""+dayBillEntity.getDayBillid();
            CharpieEntity charpie=new CharpieEntity();
            charpie.setValue(dayBillEntity.getDayIncome());
            charpie.setName(mname[0]);
            mnum.add(charpie);
            CharpieEntity charpie1=new CharpieEntity();
            charpie1.setValue(dayBillEntity.getDayOutcome());
            charpie1.setName(mname[1]);
            mnum.add(charpie1);
        }
        else{
            int billdateint=Integer.parseInt(billdate);
            //拿到日账单实体
            DayBillEntity dayBillEntity=dayBillService.getOne(new QueryWrapper<DayBillEntity>().eq("day_billid",billdateint));
            //如果不存在该日期的日账单就绘制收入0，支出0
            if(dayBillEntity==null){
                CharpieEntity charpie=new CharpieEntity();
                charpie.setName(mname[0]);
                charpie.setValue((float)0);
                mnum.add(charpie);
                CharpieEntity charpie1=new CharpieEntity();
                charpie1.setName(mname[1]);
                charpie1.setValue((float)0);
                mnum.add(charpie1);
            }
            else {
                CharpieEntity charpie=new CharpieEntity();
                charpie.setValue(dayBillEntity.getDayIncome());
                charpie.setName(mname[0]);
                mnum.add(charpie);
                CharpieEntity charpie1=new CharpieEntity();
                charpie1.setValue(dayBillEntity.getDayOutcome());
                charpie1.setName(mname[1]);
                mnum.add(charpie1);
            }
        }

        return R.ok().put("mname",mname).put("mnum",mnum).put("billdate",billdate);
    }

    /**
     * 查出收支用于绘制日账单饼图
     */
    @RequestMapping("/daybilllist")
    public R daybilllist(@RequestParam Map<String, Object> params){
        PageUtils page = dayBillService.queryPage(params);
        List zname=new ArrayList();
        List znum=new ArrayList();
        for (int i = 1; i <= dayBillService.count(); i++) {
            zname.add(dayBillService.getById(i).getDayBillid());
            znum.add(dayBillService.getById(i).getDayPure());
        }
        return R.ok().put("page", page).put("zname",zname).put("znum",znum);
    }
    /**
     * 原始信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bill_manage:daybill:info")
    public R info(@PathVariable("id") Integer id){
		DayBillEntity dayBill = dayBillService.getById(id);

        return R.ok().put("dayBill", dayBill);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bill_manage:daybill:save")
    public R save(@RequestBody DayBillEntity dayBill){
		dayBillService.save(dayBill);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bill_manage:daybill:update")
    public R update(@RequestBody DayBillEntity dayBill){
		dayBillService.updateById(dayBill);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bill_manage:daybill:delete")
    public R delete(@RequestBody Integer[] ids){
		dayBillService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


}
