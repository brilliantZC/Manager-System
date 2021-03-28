package io.renren.modules.supply_manage.controller;

import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.javaws.IconUtil;
import io.renren.common.utils.Query;
import io.renren.common.utils.ShiroUtils;
import io.renren.modules.supply_manage.entity.FbwjEntity;
import io.renren.modules.supply_manage.entity.GyuserEntity;
import io.renren.modules.supply_manage.entity.UploadPath;
import io.renren.modules.supply_manage.service.GyuserService;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.supply_manage.entity.GywjbEntity;
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
        GywjbEntity gywjbEntity1=gywjbService.getOne(new QueryWrapper<GywjbEntity>().eq("wjlxdm","AQS").eq("zztdm","0"));
        GywjbEntity gywjbEntity2=gywjbService.getOne(new QueryWrapper<GywjbEntity>().eq("wjlxdm","GYXK").eq("zztdm","0"));
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
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int time = Integer.parseInt(formatter.format(date)); //当前日期
        int num=gywjbService.count(new QueryWrapper<GywjbEntity>().eq("uid","%time%"));
        gywjbEntity1.setUid(time+num+1);gywjbEntity2.setUid(time+num+1);
		gywjbService.updateById(gywjbEntity1);
        gywjbService.updateById(gywjbEntity2);
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
            String wjlxmc = params.get("wjlxmc").toString();
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
     * 编辑申报信息，上传文件保存信息
     */
    @RequestMapping("/bjsave")
    public R bjsave(@RequestBody FbwjEntity fbwjEntity){
        //查到就更新，查不到保存
        GywjbEntity gywjb=gywjbService.getOne(new QueryWrapper<GywjbEntity>().eq("wjlxdm",fbwjEntity.getWjlxdm()).eq("ztdm",fbwjEntity.getZtdm()));
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
