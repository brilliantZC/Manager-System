package io.renren.modules.supply_manage.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.javaws.IconUtil;
import io.renren.common.utils.Query;
import io.renren.modules.supply_manage.entity.FbwjEntity;
import io.renren.modules.supply_manage.entity.GyuserEntity;
import io.renren.modules.supply_manage.entity.UploadPath;
import io.renren.modules.supply_manage.service.GyuserService;
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
import org.springframework.web.multipart.MultipartFile;


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
    @Autowired
    private GyuserService gyuserService;
    @Autowired
    private UploadPath uploadPath;

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
        //根据姓名和手机号码在供应人员表中找是否有相同的,不相同则加入供应商表
        GyuserEntity gyuserEntity=gyuserService.getOne(new QueryWrapper<GyuserEntity>().eq("gyname",gywjb.getName()).eq("gyphone",gywjb.getPhone()));
        if(gyuserEntity==null) {
            gyuserEntity.setGyname(gywjb.getName());
            gyuserEntity.setGyphone(gywjb.getPhone());
            gyuserEntity.setGyaddress(gywjb.getAddress());
            gyuserEntity.setGycount(0);
            gyuserEntity.setFlag(1);
            gyuserService.save(gyuserEntity);
        }
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

    @RequestMapping("/bjfiles")
    public R singleFileUploads(@RequestParam Map<String, Object> params, MultipartFile file) {
        String wjdz = ""; //文件地址
        String filename = ""; //上传时文件名称
        String wjmc = "";//文件名称
        try {
            String uploadDir = uploadPath.getPath(); //获得自定义的地址
            filename = file.getOriginalFilename(); //得到上传时的文件名
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }

            String name = params.get("name").toString();
            String wjlxdm = params.get("wjlxdm").toString();
            String wjlxmc = params.get("wjlxmc").toString();
            System.out.println(name+wjlxdm);
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            int time = Integer.parseInt(formatter.format(date)); //当前日期
            wjmc=wjlxmc+"-"+wjlxdm+"-"+time;

            //文件后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); //获取后缀名
            String cswjdz = (wjmc.replaceAll("\\-", "") + suffix); //在地址后加上后缀
            wjdz = uploadDir + cswjdz;

            System.out.println(wjdz+"********"+cswjdz);

            File serverFile = new File(uploadDir + cswjdz);
            file.transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok().put("Singlefile", wjdz).put("filename", filename);
    }

    /**
     * 编辑申报信息，上传文件保存信息
     */
    @RequestMapping("/bjsave")
    public R bjsave(@RequestBody FbwjEntity fbwjEntity){
        System.out.println("*************");
        System.out.println(fbwjEntity);
        //查到就更新，查不到保存
        GywjbEntity gywjb=gywjbService.getOne(new QueryWrapper<GywjbEntity>().eq("name",fbwjEntity.getName()).eq("zztdm",""));
        if(gywjb==null){
            GywjbEntity gywjbEntity=new GywjbEntity();
            gywjbEntity.setWjlxdm(fbwjEntity.getWjlxdm());  //文件类型代码
            gywjbEntity.setWjlxmc(fbwjEntity.getWjlxmc());  //文件类型名称
            gywjbEntity.setWjdz(fbwjEntity.getWjdz());
            gywjbEntity.setWjmc(fbwjEntity.getWjmc());
            gywjbEntity.setName(fbwjEntity.getName());
            gywjbEntity.setZtdm(1);
            gywjbEntity.setZtmc("已上传");
            gywjbEntity.setZztdm("0");
            gywjbEntity.setZztmc("供应商待发布");
            gywjbEntity.setName("");
            gywjbEntity.setMaterialName("");gywjbEntity.setAddress("");gywjbEntity.setJsrq("");
            gywjbEntity.setKsri("");gywjbEntity.setMaterialNum("0");gywjbEntity.setMaterialPrice((float)0);
            gywjbEntity.setPhone("");gywjbEntity.setShfs("");
            gywjbService.save(gywjbEntity);
        }
        else {
            gywjb.setWjlxdm(fbwjEntity.getWjlxdm());  //文件类型代码
            gywjb.setWjlxmc(fbwjEntity.getWjlxmc());  //文件类型名称
            gywjb.setWjdz(fbwjEntity.getWjdz());
            gywjb.setWjmc(fbwjEntity.getWjmc());
            gywjb.setName(fbwjEntity.getName());
            gywjb.setZtdm(1);
            gywjb.setZtmc("已上传");
            gywjb.setZztdm("0");
            gywjb.setZztmc("供应商待发布");
            gywjbService.updateById(gywjb);
        }

        return R.ok();
    }


}
