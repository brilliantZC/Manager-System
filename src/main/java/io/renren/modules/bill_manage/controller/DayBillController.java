package io.renren.modules.bill_manage.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 信息
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
