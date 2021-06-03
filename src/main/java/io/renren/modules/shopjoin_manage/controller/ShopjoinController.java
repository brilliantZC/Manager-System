package io.renren.modules.shopjoin_manage.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.renren.modules.supply_manage.entity.UploadPath;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.shopjoin_manage.entity.ShopjoinEntity;
import io.renren.modules.shopjoin_manage.service.ShopjoinService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-03 10:00:22
 */
@RestController
@RequestMapping("shopjoin_manage/shopjoin")
public class ShopjoinController {
    @Autowired
    private ShopjoinService shopjoinService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("shopjoin_manage:shopjoin:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shopjoinService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("shopjoin_manage:shopjoin:info")
    public R info(@PathVariable("id") Integer id){
		ShopjoinEntity shopjoin = shopjoinService.getById(id);

        return R.ok().put("shopjoin", shopjoin);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("shopjoin_manage:shopjoin:save")
    public R save(@RequestBody ShopjoinEntity shopjoin){
		shopjoinService.save(shopjoin);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("shopjoin_manage:shopjoin:update")
    public R update(@RequestBody ShopjoinEntity shopjoin){
		shopjoinService.updateById(shopjoin);

        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		shopjoinService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


}
