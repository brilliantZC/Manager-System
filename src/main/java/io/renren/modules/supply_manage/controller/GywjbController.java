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

import io.renren.modules.supply_manage.entity.GywjbEntity;
import io.renren.modules.supply_manage.service.GywjbService;
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
@RequestMapping("supply_manage/gywjb")
public class GywjbController {
    @Autowired
    private GywjbService gywjbService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("supply_manage:gywjb:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = gywjbService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("supply_manage:gywjb:info")
    public R info(@PathVariable("id") Integer id){
		GywjbEntity gywjb = gywjbService.getById(id);

        return R.ok().put("gywjb", gywjb);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("supply_manage:gywjb:save")
    public R save(@RequestBody GywjbEntity gywjb){
		gywjbService.save(gywjb);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("supply_manage:gywjb:update")
    public R update(@RequestBody GywjbEntity gywjb){
		gywjbService.updateById(gywjb);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("supply_manage:gywjb:delete")
    public R delete(@RequestBody Integer[] ids){
		gywjbService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
