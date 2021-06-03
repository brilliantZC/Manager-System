package io.renren.modules.shopjoin_manage.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.supply_manage.entity.UploadPath;
import io.renren.modules.supply_manage.entity.WjsaveEntity;
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
import org.springframework.web.multipart.MultipartFile;


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
    @Autowired
    private UploadPath uploadPath;

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
     * 列表
     */
    @RequestMapping("/sqwjlist")
    public R sqwjlist(@RequestParam Map<String, Object> params){
        PageUtils page = joinfileService.queryPage(params);
        List<JoinfileEntity> joins = new ArrayList<JoinfileEntity>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int time = Integer.parseInt(formatter.format(date)); //当前日期
        int num=joinfileService.count(new QueryWrapper<JoinfileEntity>().eq("wjlxdm","YYXK"));
        JoinfileEntity joinFileEntity1 = new JoinfileEntity();
        joinFileEntity1.setWjlx("营业许可");joinFileEntity1.setWjlxdm("YYXK");
        joinFileEntity1.setZtdm("0");joinFileEntity1.setZtmc("未上传");
        joinFileEntity1.setUid(time+num+1000);
        JoinfileEntity joinFileEntity2 = new JoinfileEntity();
        joinFileEntity2.setWjlx("卫生许可");joinFileEntity2.setWjlxdm("WSXK");
        joinFileEntity2.setZtdm("0");joinFileEntity2.setZtmc("未上传");
        joinFileEntity2.setUid(time+num+1000);
        joins.add(joinFileEntity1);
        joins.add(joinFileEntity2);
        page.setList(joins);
        return R.ok().put("page", page);
    }

    /**
     * 加盟申请文件上传
     */
    @RequestMapping("/joinfiles")
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
            String wjlxdm = params.get("wjlxdm").toString();
            String wjlx = params.get("wjlx").toString();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            int time = Integer.parseInt(formatter.format(date)); //当前日期
            wjmc=wjlx+"-"+wjlxdm+"-"+time;
            //文件后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); //获取后缀名
            String cswjdz = (wjmc.replaceAll("\\-", "") + suffix); //在地址后加上后缀
            wjdz = uploadDir + cswjdz;
            filename = wjmc+suffix;

            File serverFile = new File(uploadDir + cswjdz);
            file.transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok().put("Singlefile", wjdz).put("filename", filename);
    }

    /**
     * 加盟申请，上传文件保存信息
     */
    @RequestMapping("/joinsave")
    public R bjsave(@RequestBody JoinfileEntity joinFileEntity){
        joinfileService.save(joinFileEntity);

        return R.ok();
    }

    /**
     * 上传文件成功刷新列表
     */
    @RequestMapping("/sxwjlist")
    public R sxwjlist(@RequestParam Map<String, Object> params){
        PageUtils page = joinfileService.queryPage(params);
        String key =(String) params.get("key");
        int uid = Integer.parseInt(key);
        List<JoinfileEntity> scwjwc=new ArrayList();
        JoinfileEntity joinfileEntity1 = joinfileService.getOne(new QueryWrapper<JoinfileEntity>().eq("ztdm", 1).eq("wjlxdm", "YYXK").eq("uid",uid));
        JoinfileEntity joinfileEntity2 = joinfileService.getOne(new QueryWrapper<JoinfileEntity>().eq("ztdm",1).eq("wjlxdm","WSXK").eq("uid",uid));
        if(joinfileEntity1==null){
            JoinfileEntity joinfileEntity = new JoinfileEntity();
            joinfileEntity.setWjlx("营业许可");joinfileEntity.setWjlxdm("YYXK");
            joinfileEntity.setZtdm("0");joinfileEntity.setZtmc("未上传");
            joinfileEntity.setUid(joinfileEntity2.getUid());
            scwjwc.add(joinfileEntity);
            scwjwc.add(joinfileEntity2);
        }
        else if(joinfileEntity2==null){
            JoinfileEntity joinfileEntity = new JoinfileEntity();
            joinfileEntity.setWjlx("卫生许可");joinfileEntity.setWjlxdm("WSXK");
            joinfileEntity.setZtdm("0");joinfileEntity.setZtmc("未上传");
            joinfileEntity.setUid(joinfileEntity1.getUid());
            scwjwc.add(joinfileEntity1);
            scwjwc.add(joinfileEntity);
        }
        else {
            scwjwc.add(joinfileEntity1);
            scwjwc.add(joinfileEntity2);
        }
        page.setList(scwjwc);

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
