package io.renren.modules.goodsorder_manage.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.goods_manage.entity.GoodsEntity;
import io.renren.modules.goods_manage.service.GoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.goodsorder_manage.entity.GoodsorderEntity;
import io.renren.modules.goodsorder_manage.service.GoodsorderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-05-19 15:08:04
 */
@RestController
@RequestMapping("goodsorder_manage/goodsorder")
public class GoodsorderController {
    @Autowired
    private GoodsorderService goodsorderService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("goodsorder_manage:goodsorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsorderService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 无效订单列表
     */
    @RequestMapping("/wxlist")
    public R wxlist(@RequestParam Map<String, Object> params){
        PageUtils page = goodsorderService.wxqueryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 商家拒绝订单功能
     */
    @RequestMapping("/rejectorderlist")
    public R rejectorderlist(@RequestParam Map<String, Object> params) throws ParseException {
        String sid= (String) params.get("key");
        int id=Integer.parseInt(sid);
        String intro=(String) params.get("intro");
        GoodsorderEntity goodsorderEntity = goodsorderService.getById(id);
        goodsorderEntity.setZztdm(-1);
        goodsorderEntity.setZztmc("商家拒绝该订单");
        goodsorderEntity.setIntro(intro);

        //将开始日期转化为Date类型
        String time = goodsorderEntity.getTimeStar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        //将Date转化为时间戳
        long oldtime = date.getTime();
        Date newdate = new Date();
        long newtime = newdate.getTime();
        //得到时间戳之差
        long timestay = newtime - oldtime;
        //更具时间戳得到分钟和秒数
        int fen = (int) (timestay / 60000);
        int miao = (int) ((timestay/1000)%60);
        if(fen>60){
            int hour = fen/60;
            fen = fen%60;
            goodsorderEntity.setTimeStay(hour+"小时"+fen+"分钟"+miao+"秒");
        }
        else {
            goodsorderEntity.setTimeStay(fen+"分钟"+miao+"秒");
        }

        goodsorderService.updateById(goodsorderEntity);
        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("goodsorder_manage:goodsorder:info")
    public R info(@PathVariable("id") Integer id){
		GoodsorderEntity goodsorder = goodsorderService.getById(id);

        return R.ok().put("goodsorder", goodsorder);
    }

    /**
     * 确认订单信息
     */
    @RequestMapping("/qrorderinfo/{id}")
    public R qrorderinfo(@PathVariable("id") Integer id){
        GoodsorderEntity goodsorder = goodsorderService.getById(id);
        goodsorder.setZztmc("商家确认订单！");
        goodsorder.setZztdm(2);
        goodsorderService.updateById(goodsorder);
        return R.ok();
    }

    /**
     * 订单制作完成信息
     */
    @RequestMapping("/zzwcinfo/{id}")
    public R zzwcinfo(@PathVariable("id") Integer id){
        GoodsorderEntity goodsorder = goodsorderService.getById(id);
        goodsorder.setZztmc("订单制作完成，等待骑手接单配送！");
        goodsorder.setZztdm(3);
        goodsorderService.updateById(goodsorder);

        return R.ok();
    }
    /**
     * 骑手接单并前往商家
     */
    @RequestMapping("/jdsjinfo/{id}")
    public R jdsjinfo(@PathVariable("id") Integer id){
        GoodsorderEntity goodsorder = goodsorderService.getById(id);
        goodsorder.setZztmc("骑手接单，前往商家取货！");
        goodsorder.setZztdm(4);
        goodsorderService.updateById(goodsorder);

        return R.ok();
    }

    /**
     * 骑手取货配送
     */
    @RequestMapping("/chpsinfo/{id}")
    public R chpsinfo(@PathVariable("id") Integer id){
        GoodsorderEntity goodsorder = goodsorderService.getById(id);
        goodsorder.setZztmc("商家出货成功！骑手配送中！");
        goodsorder.setZztdm(5);
        goodsorderService.updateById(goodsorder);

        return R.ok();
    }

    /**
     * 骑手取货配送
     */
    @RequestMapping("/ddsdinfo/{id}")
    public R ddsdinfo(@PathVariable("id") Integer id){
        GoodsorderEntity goodsorder = goodsorderService.getById(id);
        goodsorder.setZztmc("骑手已送达！等待用户确认");
        goodsorder.setZztdm(6);
        goodsorderService.updateById(goodsorder);

        return R.ok();
    }

    /**
     * 用户确认收获并送达
     */
    @RequestMapping("/qrfkinfo/{id}")
    public R qrfkinfo(@PathVariable("id") Integer id) throws ParseException {
        GoodsorderEntity goodsorder = goodsorderService.getById(id);
        goodsorder.setZztmc("订单完成！");
        goodsorder.setZztdm(7);
        System.out.println(goodsorder);
        //将开始日期转化为Date类型
        String time = goodsorder.getTimeStar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        //将Date转化为时间戳
        long oldtime = date.getTime();
        Date newdate = new Date();
        long newtime = newdate.getTime();
        //得到时间戳之差
        long timestay = newtime - oldtime;
        //更具时间戳得到分钟和秒数
        int fen = (int) (timestay / 60000);
        int miao = (int) ((timestay/1000)%60);
        if(fen>60){
            int hour = fen/60;
            fen = fen%60;
            goodsorder.setTimeStay(hour+"小时"+fen+"分钟"+miao+"秒");
        }
        else {
            goodsorder.setTimeStay(fen+"分钟"+miao+"秒");
        }

        goodsorderService.updateById(goodsorder);
        return R.ok();
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("goodsorder_manage:goodsorder:save")
    public R save(@RequestBody GoodsorderEntity goodsorder){
		goodsorderService.save(goodsorder);

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/uordersave")
    public R uosave(@RequestBody GoodsorderEntity goodsorder){

        GoodsEntity goodsEntity = goodsService.getOne(new QueryWrapper<GoodsEntity>().eq("gname",goodsorder.getGoods()));
        goodsorder.setPrice(goodsEntity.getGprice());
        goodsorder.setZztdm(1);
        goodsorder.setZztmc("订单生成");
        goodsorder.setTimeStay("订单生效中...");
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        goodsorder.setTimeStar(sdf.format(date));

        goodsorderService.save(goodsorder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("goodsorder_manage:goodsorder:update")
    public R update(@RequestBody GoodsorderEntity goodsorder){
		goodsorderService.updateById(goodsorder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("goodsorder_manage:goodsorder:delete")
    public R delete(@RequestBody Integer[] ids){
		goodsorderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
