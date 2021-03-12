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

import io.renren.modules.bill_manage.entity.MonBillEntity;
import io.renren.modules.bill_manage.service.MonBillService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-10 14:49:14
 */
@RestController
@RequestMapping("bill_manage/monbill")
public class MonBillController {
    @Autowired
    private MonBillService monBillService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bill_manage:monbill:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = monBillService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表,查询日账单，用年，月
     */
    @RequestMapping("/listmon")
    @RequiresPermissions("bill_manage:monbill:listmon")
    public R listmon(@RequestParam Map<String, Object> params){
        PageUtils page = monBillService.queryPagemon(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bill_manage:monbill:info")
    public R info(@PathVariable("id") Integer id){
		MonBillEntity monBill = monBillService.getById(id);

        return R.ok().put("monBill", monBill);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bill_manage:monbill:save")
    public R save(@RequestBody MonBillEntity monBill){
		monBillService.save(monBill);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bill_manage:monbill:update")
    public R update(@RequestBody MonBillEntity monBill){
		monBillService.updateById(monBill);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bill_manage:monbill:delete")
    public R delete(@RequestBody Integer[] ids){
		monBillService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
