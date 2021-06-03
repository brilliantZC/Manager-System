package io.renren.modules.shopjoin_manage.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.shopjoin_manage.entity.JoinfileEntity;
import io.renren.modules.shopjoin_manage.service.JoinfileService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-03 18:56:15
 */
@RestController
@RequestMapping("shopjoin_manage/joinfile")
public class JoinfileController {
    @Autowired
    private JoinfileService joinfileService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("shopjoin_manage:joinfile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = joinfileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("shopjoin_manage:joinfile:info")
    public R info(@PathVariable("id") Integer id){
		JoinfileEntity joinfile = joinfileService.getById(id);

        return R.ok().put("joinfile", joinfile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("shopjoin_manage:joinfile:save")
    public R save(@RequestBody JoinfileEntity joinfile){
		joinfileService.save(joinfile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("shopjoin_manage:joinfile:update")
    public R update(@RequestBody JoinfileEntity joinfile){
		joinfileService.updateById(joinfile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("shopjoin_manage:joinfile:delete")
    public R delete(@RequestBody Integer[] ids){
		joinfileService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
