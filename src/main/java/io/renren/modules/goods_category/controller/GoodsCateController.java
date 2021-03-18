package io.renren.modules.goods_category.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.goods_category.entity.GoodsCateEntity;
import io.renren.modules.goods_category.service.GoodsCateService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-08 14:28:31
 */
@RestController
@RequestMapping("goods_category/goodscate")
public class GoodsCateController {
    @Autowired
    private GoodsCateService goodsCateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("goods_category:goodscate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsCateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("goods_category:goodscate:info")
    public R info(@PathVariable("id") Integer id){
		GoodsCateEntity goodsCate = goodsCateService.getById(id);

        return R.ok().put("goodsCate", goodsCate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("goods_category:goodscate:save")
    public R save(@RequestBody GoodsCateEntity goodsCate){
		goodsCateService.save(goodsCate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("goods_category:goodscate:update")
    public R update(@RequestBody GoodsCateEntity goodsCate){
		goodsCateService.updateById(goodsCate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("goods_category:goodscate:delete")
    public R delete(@RequestBody Integer[] ids){
		goodsCateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 将粥的种类名和数量传给前端
     */
    @RequestMapping("/tblist")
    public R tblist(@RequestParam Map<String, Object> params){
        PageUtils page = goodsCateService.queryPage(params);
        List gcname=new ArrayList();
        List gcnum= new ArrayList();
        for (int i = 10; i < 10+goodsCateService.count(); i++) {
            gcname.add(goodsCateService.getById(i).getCateName());
            gcnum.add(goodsCateService.getById(i).getCateNum());
        }

        return R.ok().put("page", page).put("gcname",gcname).put("gcnum",gcnum);
    }

}
