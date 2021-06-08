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

import io.renren.modules.shopjoin_manage.entity.ShopjoinTpEntity;
import io.renren.modules.shopjoin_manage.service.ShopjoinTpService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-08 20:54:40
 */
@RestController
@RequestMapping("shopjoin_manage/shopjointp")
public class ShopjoinTpController {
    @Autowired
    private ShopjoinTpService shopjoinTpService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("shopjoin_manage:shopjointp:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shopjoinTpService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("shopjoin_manage:shopjointp:info")
    public R info(@PathVariable("id") Integer id){
		ShopjoinTpEntity shopjoinTp = shopjoinTpService.getById(id);

        return R.ok().put("shopjoinTp", shopjoinTp);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("shopjoin_manage:shopjointp:save")
    public R save(@RequestBody ShopjoinTpEntity shopjoinTp){
		shopjoinTpService.save(shopjoinTp);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("shopjoin_manage:shopjointp:update")
    public R update(@RequestBody ShopjoinTpEntity shopjoinTp){
		shopjoinTpService.updateById(shopjoinTp);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("shopjoin_manage:shopjointp:delete")
    public R delete(@RequestBody Integer[] ids){
		shopjoinTpService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
