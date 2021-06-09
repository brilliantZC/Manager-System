package io.renren.modules.shopjoin_manage.controller;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.shopjoin_manage.entity.JoinfileEntity;
import io.renren.modules.shopjoin_manage.service.JoinfileService;
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
    @Autowired
    private JoinfileService joinfileService;


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
     * 提交申请信息
     */
    @RequestMapping("/tjsqinfo/{id}")
    public R tjsqinfo(@PathVariable("id") Integer id){
        ShopjoinEntity shopjoin = shopjoinService.getById(id);
        shopjoin.setZztdm(2);shopjoin.setZztmc("加盟商已提交审核");shopjoin.setAddressResult("");
        shopjoinService.updateById(shopjoin);
        return R.ok().put("shopjoin", shopjoin);
    }

    /**
     * 实地考察通过信息
     */
    @RequestMapping("/accessinfo/{id}")
    public R accessinfo(@PathVariable("id") Integer id){
        ShopjoinEntity shopjoin = shopjoinService.getById(id);
        shopjoin.setZztdm(3);shopjoin.setZztmc("实地考察通过");shopjoin.setAddressResult("通过");
        shopjoinService.updateById(shopjoin);
        return R.ok().put("shopjoin", shopjoin);
    }

    /**
     * 实地考察驳回信息
     */
    /**
     * 列表
     */
    @RequestMapping("/faillist")
    public R faillist(@RequestParam Map<String, Object> params){
        String reslut = (String) params.get("addresult");
        int id = Integer.parseInt((String) params.get("id"));
        ShopjoinEntity shopjoinEntity = shopjoinService.getById(id);
        shopjoinEntity.setAddressResult(reslut);
        shopjoinEntity.setZztmc("加盟商已填写申请");shopjoinEntity.setZztdm(1);
        shopjoinService.updateById(shopjoinEntity);
        return R.ok();
    }

    /**
     * 实地考察通过信息
     */
    @RequestMapping("/tpshinfo/{id}")
    public R tpshinfo(@PathVariable("id") Integer id){
        ShopjoinEntity shopjoin = shopjoinService.getById(id);
        shopjoin.setZztdm(5);shopjoin.setZztmc("加盟商提交投票审核");
        shopjoinService.updateById(shopjoin);
        return R.ok().put("shopjoin", shopjoin);
    }



    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("shopjoin_manage:shopjoin:save")
    public R save(@RequestBody ShopjoinEntity shopjoin){
        //ShopjoinEntity(id=null, name=章超, phone=18226607243, address=安徽省广德市桃州镇万桂山南路12号, money=20万, yyxk=, yyxkztdm=null, wsxk=, wsxkztdm=null, addressResult=, grxd=, grxdztdm=null, tpResult=, finalResult=, finalTime=, zztdm=null, zztmc=, uid=20211604)
        JoinfileEntity joinfileEntity1 = joinfileService.getOne(new QueryWrapper<JoinfileEntity>().eq("wjlxdm","YYXK").eq("uid",shopjoin.getUid()));
        JoinfileEntity joinfileEntity2 = joinfileService.getOne(new QueryWrapper<JoinfileEntity>().eq("wjlxdm","WSXK").eq("uid",shopjoin.getUid()));
        shopjoin.setYyxk(joinfileEntity1.getWjdz());shopjoin.setYyxkztdm(Integer.parseInt(joinfileEntity1.getZtdm()));
        shopjoin.setWsxk(joinfileEntity2.getWjdz());shopjoin.setWsxkztdm(Integer.parseInt(joinfileEntity2.getZtdm()));
        shopjoin.setZztdm(1);shopjoin.setZztmc("加盟商已填写申请");
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
     * 删除,顺便将Joinfile表中的文件信息也删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){

        for (int i: ids ) {
            ShopjoinEntity shopjoinEntity = shopjoinService.getById(i);
            List<JoinfileEntity> joinfileEntities = joinfileService.list(new QueryWrapper<JoinfileEntity>().eq("uid",shopjoinEntity.getUid()));
            for (int j = 0; j < joinfileService.count(); j++) {
                joinfileService.removeById(joinfileEntities.get(j).getId());
            }
        }
        shopjoinService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


}
