package io.renren.modules.supply_manage.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.supply_manage.entity.FbwjEntity;
import io.renren.modules.supply_manage.entity.GywjbEntity;
import io.renren.modules.supply_manage.entity.WjsaveEntity;
import io.renren.modules.supply_manage.service.GywjbService;
import io.renren.modules.supply_manage.service.WjsaveService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.supply_manage.entity.WjlxbEntity;
import io.renren.modules.supply_manage.service.WjlxbService;
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
@RequestMapping("supply_manage/wjlxb")
public class WjlxbController {
    @Autowired
    private WjlxbService wjlxbService;
    @Autowired
    private GywjbService gywjbService;
    @Autowired
    private WjsaveService wjsaveService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("supply_manage:wjlxb:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wjlxbService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 发布阶段文件
     * @param params
     * @return
     */
    @RequestMapping("/fbwjlist")
    public R fbwjlist(@RequestParam Map<String, Object> params){
        PageUtils page = wjsaveService.fbqueryPage(params);
        List<WjsaveEntity> wjsaveEntities = new ArrayList();
        WjsaveEntity wjsaveEntity1=new WjsaveEntity();
        wjsaveEntity1.setWjlx("安全书");wjsaveEntity1.setWjlxdm("AQS");wjsaveEntity1.setWjdz("");
        wjsaveEntity1.setZtdm(0);wjsaveEntity1.setZtmc("未上传");wjsaveEntity1.setWjmc("");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int time = Integer.parseInt(formatter.format(date)); //当前日期
        int num=wjsaveService.count(new QueryWrapper<WjsaveEntity>().eq("wjlxdm","AQS"));
        System.out.println(num);
        wjsaveEntity1.setUid(time+num+1000);
        WjsaveEntity wjsaveEntity2=new WjsaveEntity();
        wjsaveEntity2.setWjlx("供应许可");wjsaveEntity2.setWjlxdm("GYXK");wjsaveEntity2.setWjdz("");
        wjsaveEntity2.setZtdm(0);wjsaveEntity2.setZtmc("未上传");wjsaveEntity2.setWjmc("");
        wjsaveEntity2.setUid(time+num+1000);
        wjsaveEntities.add(wjsaveEntity1);wjsaveEntities.add(wjsaveEntity2);
        page.setList(wjsaveEntities);
        return R.ok().put("page", page);
    }

    /**
     * 发布阶段文件:上传文件刷新数据
     * @param params
     * @return
     */
    @RequestMapping("/scwjlist")
    public R scwjlist(@RequestParam Map<String, Object> params){
        PageUtils page = wjsaveService.fbqueryPage(params);
        String key =(String) params.get("key");
        int uid = Integer.parseInt(key);
        List<WjsaveEntity> scwjwc=new ArrayList();
        scwjwc.add(wjsaveService.getOne(new QueryWrapper<WjsaveEntity>().eq("ztdm", 1).eq("wjlxdm", "AQS").eq("uid",uid)));
        scwjwc.add(wjsaveService.getOne(new QueryWrapper<WjsaveEntity>().eq("ztdm",1).eq("wjlxdm","GYXK").eq("uid",uid)));
        page.setList(scwjwc);
        return R.ok().put("page", page);
    }

    /**
     * 发布阶段文件
     * @param params
     * @return
     */
    @RequestMapping("/ghdjlist")
    public R ghdjlist(@RequestParam Map<String, Object> params){
        PageUtils page = wjlxbService.fbqueryPage(params);
        List<FbwjEntity> fbwjEntities = new ArrayList();
        FbwjEntity fbwjEntity1=new FbwjEntity();
        fbwjEntity1.setWjmc("");fbwjEntity1.setWjdz("");fbwjEntity1.setWjlxdm("AQS");fbwjEntity1.setWjlxmc("安全书");
        fbwjEntity1.setZtdm("0");fbwjEntity1.setZtmc("未上传");fbwjEntity1.setZztdm("0");fbwjEntity1.setZztmc("供应商发布");
        fbwjEntities.add(fbwjEntity1);
        FbwjEntity fbwjEntity2=new FbwjEntity();
        fbwjEntity2.setWjmc("");fbwjEntity2.setWjdz("");fbwjEntity2.setWjlxdm("GYXK");fbwjEntity2.setWjlxmc("供应许可");fbwjEntity2.setZtdm("0");
        fbwjEntity2.setZtmc("未上传");fbwjEntity2.setZztdm("0");fbwjEntity2.setZztmc("供应商发布");fbwjEntities.add(fbwjEntity2);
        page.setList(fbwjEntities);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("supply_manage:wjlxb:info")
    public R info(@PathVariable("id") Integer id){
		WjlxbEntity wjlxb = wjlxbService.getById(id);

        return R.ok().put("wjlxb", wjlxb);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("supply_manage:wjlxb:save")
    public R save(@RequestBody WjlxbEntity wjlxb){
        WjlxbEntity wjlxbEntity=wjlxbService.getOne(new QueryWrapper<WjlxbEntity>().eq("wjlxdm",wjlxb.getWjlxdm()).or().eq("jdmc",wjlxb.getJddm()));
		if(wjlxbEntity!=null){
		    return R.error("已存在！！！");
        }
        else {
            wjlxbService.save(wjlxb);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("supply_manage:wjlxb:update")
    public R update(@RequestBody WjlxbEntity wjlxb){
		wjlxbService.updateById(wjlxb);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("supply_manage:wjlxb:delete")
    public R delete(@RequestBody Integer[] ids){
		wjlxbService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
