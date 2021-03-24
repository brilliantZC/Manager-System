package io.renren.modules.supply_manage.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        if(gyuserEntity==null){
            gyuserEntity.setGyname(gywjb.getName());
            gyuserEntity.setGyphone(gywjb.getPhone());
            gyuserEntity.setGyaddress(gywjb.getAddress());
            gyuserEntity.setGycount(0);
            gyuserEntity.setFlag(1);
            gyuserService.save(gyuserEntity);
        }
        gywjb.setZtdm(1);
        gywjb.setZtmc("已上传");
        gywjb.setWjdz("");
        gywjb.setWjmc("");
        gywjb.setZztdm("");
        gywjb.setZztmc("");
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
        /* String wjdz = ""; //文件地址
        String filename = ""; //文件名称
        String newzlwj = "";
        try {
            String uploadDir = uploadPath.getPath(); //获得自定义的地址
            filename = file.getOriginalFilename(); //得到上传时的文件名
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //服务器端保存的文件对象
            String xmsbdm = params.get("xmsbdm").toString();
            String zllxdm = params.get("zllxdm").toString();
           ZlXmwjbEntity zlXmwjbEntity = zlXmwjbService.selectOne(new EntityWrapper<ZlXmwjbEntity>().eq("xmsbdm",xmsbdm).eq("zllxdm",zllxdm));

            if (zlXmwjbEntity==null){
//                Long newmaxid = jdbcTemplate.queryForObject(String.format("select auto_increment from information_schema.tables where table_schema='qemgr' and table_name='zl_xmwjb'"), Long.class);
                String nd = params.get("nd").toString();
                newzlwj = xmsbdm + "_" +zllxdm+"_"+ nd;
            }else {
                String zlwj = zlXmwjbEntity.getZlwj();
                newzlwj = zlwj;
            }
            //文件后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); //获取后缀名
            String cswjdz = (newzlwj.toString().replaceAll("\\-", "") + suffix); //在地址后加上后缀
            wjdz = uploadDir + cswjdz;
            File serverFile = new File(uploadDir + cswjdz);
            file.transferTo(serverFile);
            logger.debug("文件写入成功...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok().put("Singlefile", wjdz).put("filename", filename);
        }*/
        return R.ok();
    }

}
