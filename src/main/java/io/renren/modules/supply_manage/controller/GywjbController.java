package io.renren.modules.supply_manage.controller;

import java.io.*;

import java.net.URLEncoder;

import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.renren.modules.supply_manage.entity.*;
import io.renren.modules.supply_manage.service.GyuserService;
import io.renren.modules.supply_manage.service.WjsaveService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.supply_manage.service.GywjbService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


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
    @Autowired
    private WjsaveService wjsaveService;

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
     * 供应阶段，只用展示安全书一条实例列表
     */
    @RequestMapping("/gylist")
    public R gylist(@RequestParam Map<String, Object> params){
        PageUtils page = gywjbService.gyqueryPage(params);

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
     * 选购信息
     */
    @RequestMapping("/xginfo/{id}")
    public R xginfo(@PathVariable("id") Integer id){
        GywjbEntity gywjb = gywjbService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("key",gywjb.getUid());
        PageUtils page = gywjbService.xgqueryPage(map);
        List<GywjbEntity> gywjbEntities= (List<GywjbEntity>) page.getList();
        for (GywjbEntity e : gywjbEntities) {
            e.setZztdm("3");
            e.setZztmc("已选购");
            gywjbService.updateById(e);
        }
        return R.ok();
    }

    /**
     * 成功信息
     */
    @RequestMapping("/cginfo/{id}")
    public R cginfo(@PathVariable("id") Integer id){
        GywjbEntity gywjb = gywjbService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("key",gywjb.getUid());
        PageUtils page = gywjbService.xgqueryPage(map);
        List<GywjbEntity> gywjbEntities= (List<GywjbEntity>) page.getList();
        for (GywjbEntity e : gywjbEntities) {
            e.setZztdm("6");
            e.setZztmc("交易完成");
            gywjbService.updateById(e);
        }
        return R.ok();
    }

    /**
     * 成功信息
     */
    @RequestMapping("/qrinfo/{id}")
    public R qrinfo(@PathVariable("id") Integer id){
        GywjbEntity gywjb = gywjbService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("key",gywjb.getUid());
        PageUtils page = gywjbService.xgqueryPage(map);
        List<GywjbEntity> gywjbEntities= (List<GywjbEntity>) page.getList();
        for (GywjbEntity e : gywjbEntities) {
            e.setZztdm("4");
            e.setZztmc("确认供货");
            gywjbService.updateById(e);
        }
        return R.ok();
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
            GyuserEntity user=new GyuserEntity();
            user.setGyname(gywjb.getName());
            user.setGyphone(gywjb.getPhone());
            user.setGyaddress(gywjb.getAddress());
            user.setGycount(1);
            user.setFlag(1);
            gyuserService.save(user);
        }
        else{
            //若有此用户，交易次数加一
            gyuserEntity.setGycount(gyuserEntity.getGycount()+1);
            gyuserService.updateById(gyuserEntity);
        }
        //更新gywjb剩余内容
        GywjbEntity gywjbEntity1=new GywjbEntity();
        GywjbEntity gywjbEntity2=new GywjbEntity();
        gywjbEntity1.setName(gywjb.getName()); gywjbEntity2.setName(gywjb.getName());
        gywjbEntity1.setPhone(gywjb.getPhone());gywjbEntity2.setPhone(gywjb.getPhone());
        gywjbEntity1.setAddress(gywjb.getAddress());gywjbEntity2.setAddress(gywjb.getAddress());
        gywjbEntity1.setMaterialName(gywjb.getMaterialName());gywjbEntity2.setMaterialName(gywjb.getMaterialName());
        gywjbEntity1.setMaterialPrice(gywjb.getMaterialPrice());gywjbEntity2.setMaterialPrice(gywjb.getMaterialPrice());
        gywjbEntity1.setMaterialNum(gywjb.getMaterialNum());gywjbEntity2.setMaterialNum(gywjb.getMaterialNum());
        gywjbEntity1.setKsri(gywjb.getKsri());gywjbEntity2.setKsri(gywjb.getKsri());
        gywjbEntity1.setJsrq(gywjb.getJsrq());gywjbEntity2.setJsrq(gywjb.getJsrq());
        gywjbEntity1.setShfs(gywjb.getShfs()); gywjbEntity2.setShfs(gywjb.getShfs());
        gywjbEntity1.setIntro(gywjb.getIntro()); gywjbEntity2.setIntro(gywjb.getIntro());
        gywjbEntity1.setZztmc("供应商发布");gywjbEntity2.setZztmc("供应商发布");
        gywjbEntity1.setZztdm("1");gywjbEntity2.setZztdm("1");
        gywjbEntity1.setUid(gywjb.getUid());gywjbEntity2.setUid(gywjb.getUid());
        WjsaveEntity wjsaveEntity1=wjsaveService.getOne(new QueryWrapper<WjsaveEntity>().eq("uid",gywjb.getUid()).eq("wjlxdm","AQS"));
        WjsaveEntity wjsaveEntity2=wjsaveService.getOne(new QueryWrapper<WjsaveEntity>().eq("uid",gywjb.getUid()).eq("wjlxdm","GYXK"));
        gywjbEntity1.setZtmc(wjsaveEntity1.getZtmc());gywjbEntity2.setZtmc(wjsaveEntity2.getZtmc());
        gywjbEntity1.setZtdm(wjsaveEntity1.getZtdm());gywjbEntity2.setZtdm(wjsaveEntity2.getZtdm());
        gywjbEntity1.setWjmc(wjsaveEntity1.getWjmc());gywjbEntity2.setWjmc(wjsaveEntity2.getWjmc());
        gywjbEntity1.setWjdz(wjsaveEntity1.getWjdz());gywjbEntity2.setWjdz(wjsaveEntity2.getWjdz());
        gywjbEntity1.setWjlxmc("安全书");gywjbEntity2.setWjlxmc("供应许可");
        gywjbEntity1.setWjlxdm("AQS");gywjbEntity2.setWjlxdm("GYXK");
		gywjbService.save(gywjbEntity1);
        gywjbService.save(gywjbEntity2);
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
     * 编辑申报信息，上传文件保存信息
     */
    @RequestMapping("/bjsave")
    public R bjsave(@RequestBody WjsaveEntity wjsaveEntity){
        //查到就更新，查不到保存
        WjsaveEntity wjsave=wjsaveService.getOne(new QueryWrapper<WjsaveEntity>().eq("uid",wjsaveEntity.getUid()).eq("wjlxdm",wjsaveEntity.getWjlxdm()));
        if(wjsave==null){
            WjsaveEntity wjsaveEntity1 = new WjsaveEntity();
            wjsaveEntity1.setUid(wjsaveEntity.getUid());
            wjsaveEntity1.setWjmc(wjsaveEntity.getWjmc());
            wjsaveEntity1.setZtmc("已上传");
            wjsaveEntity1.setZtdm(wjsaveEntity.getZtdm());
            wjsaveEntity1.setWjlxdm(wjsaveEntity.getWjlxdm());
            wjsaveEntity1.setWjdz(wjsaveEntity.getWjdz());
            wjsaveEntity1.setWjlx(wjsaveEntity.getWjlx());
            wjsaveService.save(wjsaveEntity1);
            System.out.println(wjsaveEntity1);
        }
        else {
            wjsave.setUid(wjsaveEntity.getUid());
            wjsave.setWjmc(wjsaveEntity.getWjmc());
            wjsave.setZtmc("已上传");
            wjsave.setZtdm(1);
            wjsave.setWjlxdm(wjsaveEntity.getWjlxdm());
            wjsave.setWjdz(wjsaveEntity.getWjdz());
            wjsave.setWjlx(wjsaveEntity.getWjlx());
            wjsaveService.updateById(wjsave);
            System.out.println(wjsave);
        }

        return R.ok();
    }

    /**
     * 供货单据上传
     */
    @RequestMapping("/ghfiles")
    public R ghsingleFileUploads(@RequestParam Map<String, Object> params, MultipartFile file) {
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
            String wjlxdm = "GHDJ";
            String wjlxmc = "供货单据";
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            int time = Integer.parseInt(formatter.format(date)); //当前日期
            wjmc=wjlxmc+"-"+wjlxdm+"-"+time;

            //文件后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); //获取后缀名
            String cswjdz = (wjmc.replaceAll("\\-", "") + suffix); //在地址后加上后缀
            wjdz = uploadDir + cswjdz;

            File serverFile = new File(uploadDir + cswjdz);
            file.transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok().put("Singlefile", wjdz).put("filename", filename);
    }

    /**
     * 上传供货单据，上传文件保存信息
     */
    @RequestMapping("/ghsave")
    public R ghsave(@RequestBody FbwjEntity fbwjEntity){
        GywjbEntity gywjbEntity=new GywjbEntity();
        GywjbEntity gywjbEntity1=gywjbService.getOne(new QueryWrapper<GywjbEntity>().eq("uid",fbwjEntity.getUid()).eq("wjlxdm","AQS"));
        GywjbEntity gywjbEntity2=gywjbService.getOne(new QueryWrapper<GywjbEntity>().eq("uid",fbwjEntity.getUid()).eq("wjlxdm","GYXK"));
        gywjbEntity.setWjlxdm("GHDJ");  //文件类型代码
        gywjbEntity.setWjlxmc("供货单据");  //文件类型名称
        gywjbEntity.setWjdz(fbwjEntity.getWjdz());
        gywjbEntity.setWjmc(fbwjEntity.getWjmc());
        gywjbEntity.setName(gywjbEntity1.getName());
        gywjbEntity.setZtdm(1);
        gywjbEntity.setZtmc("已上传");
        gywjbEntity.setZztdm("5");gywjbEntity1.setZztmc("上传供货单据");gywjbEntity2.setZztmc("上传供货单据");
        gywjbEntity.setZztmc("上传供货单据");gywjbEntity1.setZztdm("5");gywjbEntity2.setZztdm("5");
        gywjbEntity.setMaterialName(gywjbEntity1.getMaterialName());gywjbEntity.setAddress(gywjbEntity1.getAddress());gywjbEntity.setJsrq(gywjbEntity1.getJsrq());
        gywjbEntity.setKsri(gywjbEntity1.getKsri());gywjbEntity.setMaterialNum(gywjbEntity1.getMaterialNum());gywjbEntity.setMaterialPrice(gywjbEntity1.getMaterialPrice());
        gywjbEntity.setPhone(gywjbEntity1.getPhone());gywjbEntity.setShfs(gywjbEntity1.getShfs());
        gywjbEntity.setUid(fbwjEntity.getUid());
        gywjbEntity.setIntro(gywjbEntity1.getIntro());
        gywjbService.save(gywjbEntity);
        gywjbService.updateById(gywjbEntity1);
        gywjbService.updateById(gywjbEntity2);

        return R.ok();
    }

    /**
     * 文件下载
     */
    @RequestMapping( value = "/downloads/{wjdz}",method = RequestMethod.POST)
    public void testDownload (@PathVariable String wjdz, HttpServletResponse response) throws UnsupportedEncodingException {
        String xx = uploadPath.getPath(); //文件的上传地址（文件夹）C:/upload/
        String dir =xx+wjdz; //文件目录 C:/upload/2018SB0078_SBCL_180.docx
        File file=new File(dir);     //1.获取要下载的文件的绝对路径 C:/upload/2018SB0078_SBCL_180.docx
        String newDname=wjdz;
        //2.获取要下载的文件名 2018SB0078_SBCL_180.docx

        FileInputStream fileIn = null;
        try {
            fileIn =new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(file.exists()) {  //判断文件父目录是否存在
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename="  + URLEncoder.encode( newDname,"UTF-8"));  //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            byte[] buff = new byte[1024];    //5.创建数据缓冲区
            BufferedInputStream bis = null;
            OutputStream os = null;
            int readTmp = 0;
            try {
                os = response.getOutputStream(); //6.通过response对象获取OutputStream流
                bis = new BufferedInputStream(new FileInputStream(file));     //4.根据文件路径获取要下载的文件输入流
                int i = bis.read(buff);         //7.将FileInputStream流写入到buffer缓冲区
                while ((readTmp =fileIn.read(buff)) != -1){
                    os.write(buff,0,readTmp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileIn.close();
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
