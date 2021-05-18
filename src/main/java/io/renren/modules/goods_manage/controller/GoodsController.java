package io.renren.modules.goods_manage.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.goods_category.entity.GoodsCateEntity;
import io.renren.modules.goods_category.service.GoodsCateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.goods_manage.entity.GoodsEntity;
import io.renren.modules.goods_manage.service.GoodsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-31 17:45:28
 */
@RestController
@RequestMapping("goods_manage/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsCateService goodsCateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("goods_manage:goods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("goods_manage:goods:info")
    public R info(@PathVariable("id") Integer id){
		GoodsEntity goods = goodsService.getById(id);

        return R.ok().put("goods", goods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("goods_manage:goods:save")
    public R save(@RequestBody GoodsEntity goods){
		goodsService.save(goods);

		//将保存的类别加一:先找出这个类别实体，在修改

        //得到类别实体
        QueryWrapper<GoodsCateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cate_name", goods.getGintro());
        GoodsCateEntity goodscatechange = goodsCateService.getOne(queryWrapper);

        //刚加的类别数量加一更新 GoodsCateEntity(id=11, cateName=全素粥类, cateNum=2, cateIntro=素粥，甜粥)
        goodscatechange.setCateNum(goodscatechange.getCateNum()+1);

        //更具id更新
        goodsCateService.updateById(goodscatechange);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("goods_manage:goods:update")
    public R update(@RequestBody GoodsEntity goods){
		goodsService.updateById(goods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("goods_manage:goods:delete")
    public R delete(@RequestBody Integer[] ids){
		goodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
