package io.renren.modules.goodsorder_manage.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.goods_manage.entity.GoodsEntity;
import io.renren.modules.goods_manage.service.GoodsService;
import io.renren.modules.supply_manage.entity.GywjbEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.goodsorder_manage.entity.GoodsorderEntity;
import io.renren.modules.goodsorder_manage.service.GoodsorderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-05-19 15:08:04
 */
@RestController
@RequestMapping("goodsorder_manage/goodsorder")
public class GoodsorderController {
    @Autowired
    private GoodsorderService goodsorderService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("goodsorder_manage:goodsorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsorderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("goodsorder_manage:goodsorder:info")
    public R info(@PathVariable("id") Integer id){
		GoodsorderEntity goodsorder = goodsorderService.getById(id);

        return R.ok().put("goodsorder", goodsorder);
    }

    /**
     * 确认订单信息
     */
    @RequestMapping("/qrorderinfo/{id}")
    public R qrorderinfo(@PathVariable("id") Integer id){
        GoodsorderEntity goodsorder = goodsorderService.getById(id);
        goodsorder.setZztmc("商家确认订单");
        goodsorder.setZztdm(2);
        goodsorderService.updateById(goodsorder);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("goodsorder_manage:goodsorder:save")
    public R save(@RequestBody GoodsorderEntity goodsorder){
		goodsorderService.save(goodsorder);

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/uordersave")
    public R uosave(@RequestBody GoodsorderEntity goodsorder){

        GoodsEntity goodsEntity = goodsService.getOne(new QueryWrapper<GoodsEntity>().eq("gname",goodsorder.getGoods()));
        goodsorder.setPrice(goodsEntity.getGprice());
        goodsorder.setZztdm(1);
        goodsorder.setZztmc("订单生成");
        goodsorder.setTimeStay("商家正在接单");
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        goodsorder.setTimeStar(sdf.format(date));

        goodsorderService.save(goodsorder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("goodsorder_manage:goodsorder:update")
    public R update(@RequestBody GoodsorderEntity goodsorder){
		goodsorderService.updateById(goodsorder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("goodsorder_manage:goodsorder:delete")
    public R delete(@RequestBody Integer[] ids){
		goodsorderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}