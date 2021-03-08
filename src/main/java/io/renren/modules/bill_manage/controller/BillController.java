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

import io.renren.modules.bill_manage.entity.BillEntity;
import io.renren.modules.bill_manage.service.BillService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-08 20:23:36
 */
@RestController
@RequestMapping("bill_manage/bill")
public class BillController {
    @Autowired
    private BillService billService;

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
