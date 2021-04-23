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

import io.renren.modules.supply_manage.entity.WjsaveEntity;
import io.renren.modules.supply_manage.service.WjsaveService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-04-23 09:54:11
 */
@RestController
@RequestMapping("supply_manage/wjsave")
public class WjsaveController {
    @Autowired
    private WjsaveService wjsaveService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("supply_manage:wjsave:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wjsaveService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("supply_manage:wjsave:info")
    public R info(@PathVariable("id") Integer id){
		WjsaveEntity wjsave = wjsaveService.getById(id);

        return R.ok().put("wjsave", wjsave);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("supply_manage:wjsave:save")
    public R save(@RequestBody WjsaveEntity wjsave){
		wjsaveService.save(wjsave);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("supply_manage:wjsave:update")
    public R update(@RequestBody WjsaveEntity wjsave){
		wjsaveService.updateById(wjsave);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("supply_manage:wjsave:delete")
    public R delete(@RequestBody Integer[] ids){
		wjsaveService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
