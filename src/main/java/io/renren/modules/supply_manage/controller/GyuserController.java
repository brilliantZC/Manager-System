package io.renren.modules.supply_manage.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.supply_manage.entity.GyuserEntity;
import io.renren.modules.supply_manage.service.GyuserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-22 21:24:14
 */
@RestController
@RequestMapping("supply_manage/gyuser")
public class GyuserController {
    @Autowired
    private GyuserService gyuserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("supply_manage:gyuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = gyuserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("supply_manage:gyuser:info")
    public R info(@PathVariable("id") Integer id){
		GyuserEntity gyuser = gyuserService.getById(id);

        return R.ok().put("gyuser", gyuser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("supply_manage:gyuser:save")
    public R save(@RequestBody GyuserEntity gyuser){
		gyuserService.save(gyuser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("supply_manage:gyuser:update")
    public R update(@RequestBody GyuserEntity gyuser){
		gyuserService.updateById(gyuser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("supply_manage:gyuser:delete")
    public R delete(@RequestBody Integer[] ids){
		gyuserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
