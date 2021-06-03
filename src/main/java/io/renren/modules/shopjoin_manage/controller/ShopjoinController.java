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
    @Autowired
    private UploadPath uploadPath;

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
     * 列表
     */
    /*@RequestMapping("/sqwjlist")
    public R sqwjlist(@RequestParam Map<String, Object> params){
        PageUtils page = shopjoinService.queryPage(params);
        List<JoinFileEntity> joins = new ArrayList<JoinFileEntity>();
        JoinFileEntity joinFileEntity1 = new JoinFileEntity("营业许可","YYXK","","","0","未上传");
        JoinFileEntity joinFileEntity2 = new JoinFileEntity("卫生许可","WSXK","","","0","未上传");
        joins.add(joinFileEntity1);
        joins.add(joinFileEntity2);
        page.setList(joins);

        return R.ok().put("page", page);
    }*/

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
    /*@RequestMapping("/joinsave")
    public R bjsave(@RequestBody JoinFileEntity joinFileEntity){
        System.out.println(joinFileEntity);

        return R.ok();
    }*/

    /**
     * 上传文件成功刷新列表
     */
    @RequestMapping("/sxwjlist")
    public R sxwjlist(@RequestParam Map<String, Object> params){
       /* PageUtils page = shopjoinService.queryPage(params);
        List<JoinFileEntity> joins = new ArrayList<JoinFileEntity>();
        JoinFileEntity joinFileEntity1 = new JoinFileEntity("营业许可","YYXK","","","0","未上传");
        JoinFileEntity joinFileEntity2 = new JoinFileEntity("卫生许可","WSXK","","","0","未上传");
        joins.add(joinFileEntity1);
        joins.add(joinFileEntity2);
        page.setList(joins);*/

        return R.ok();
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
    @RequiresPermissions("shopjoin_manage:shopjoin:delete")
    public R delete(@RequestBody Integer[] ids){
		shopjoinService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
