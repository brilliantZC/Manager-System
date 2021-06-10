package io.renren.modules.shopjoin_manage.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.ShiroUtils;
import io.renren.modules.shopjoin_manage.entity.ShopjoinEntity;
import io.renren.modules.shopjoin_manage.service.ShopjoinService;
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
    @Autowired
    private ShopjoinService shopjoinService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shopjoinTpService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		ShopjoinTpEntity shopjoinTp = shopjoinTpService.getById(id);

        return R.ok().put("shopjoinTp", shopjoinTp);
    }

    /**
     * 专家投票通过信息
     */
    @RequestMapping("/passinfo/{id}")
    public R passinfo(@PathVariable("id") Integer id){
        //ShopjoinTpEntity shopjoinTp = shopjoinTpService.getById(id);
        //SysUserEntity(userId=1, username=admin, password=9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d, salt=YzcmCZNvbXocrsz9dm8e, email=root@renren.io, mobile=13612345678, status=1, roleIdList=null, createUserId=1, createTime=Fri Nov 11 11:11:11 CST 2016)
        //ShopjoinEntity(id=1, name=章超, phone=18226607243, address=安徽省广德市桃州镇万桂山南路12号, money=20万, yyxk=C:/upload/营业许可YYXK20210604.doc, yyxkztdm=1, wsxk=C:/upload/卫生许可WSXK20210604.doc, wsxkztdm=1, addressResult=通过, grxd=C:/upload/个人信贷GRXD20210608.doc, grxdztdm=1, tpResult=, finalResult=, finalTime=, zztdm=4, zztmc=加盟商已上传个人信贷！, uid=20211604)
        ShopjoinEntity shopjoinEntity = shopjoinService.getById(id);
        //1.非专家不可以投票(用显示控制)
        //2.总共3个专家，若当前项目投票数少于3，则还有专家没投票
        if(shopjoinTpService.count(new QueryWrapper<ShopjoinTpEntity>().eq("uid",shopjoinEntity.getUid()))<3){
            //判断当前专家对于该项目是否投过票，若没投就投
            if(shopjoinTpService.getOne(new QueryWrapper<ShopjoinTpEntity>().eq("uid",shopjoinEntity.getUid()).eq("name",ShiroUtils.getUserEntity().getUsername()))==null){
                ShopjoinTpEntity shopjoinTpEntity = new ShopjoinTpEntity();
                shopjoinTpEntity.setName(ShiroUtils.getUserEntity().getUsername());
                shopjoinTpEntity.setResult(1);shopjoinTpEntity.setUid(shopjoinEntity.getUid());
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                int time = Integer.parseInt(formatter.format(date));
                shopjoinTpEntity.setTime(time+"");
                shopjoinTpService.save(shopjoinTpEntity);
                //投完票看是否该项目已经投完
                if(shopjoinTpService.count(new QueryWrapper<ShopjoinTpEntity>().eq("uid",shopjoinEntity.getUid()))==3) {
                    List<ShopjoinTpEntity> list = shopjoinTpService.list(new QueryWrapper<ShopjoinTpEntity>().eq("uid", shopjoinEntity.getUid()));
                    int result = 0;
                    result += list.get(0).getResult();
                    result += list.get(1).getResult();
                    result += list.get(2).getResult();
                    if (result >= 2) {
                        shopjoinEntity.setTpResult("通过");
                        shopjoinEntity.setZztmc("专家已投票，投票通过");
                        shopjoinEntity.setZztdm(6);
                        shopjoinService.updateById(shopjoinEntity);
                    } else {
                        shopjoinEntity.setTpResult("投票未通过");
                        shopjoinEntity.setZztmc("专家已投票，投票未通过");
                        shopjoinEntity.setZztdm(4);
                        shopjoinService.updateById(shopjoinEntity);
                        //删除之前的投票信息，重新投票
                        for (int i = 0; i < 3; i++) {
                            shopjoinTpService.removeById(list.get(i).getId());
                        }

                    }
                    return R.ok();
                }
            }else {
                //重复专家投票
                return R.error("该专家已对该申请投过票了！");
            }
        }
        return R.ok();
    }

    /**
     * 专家投票不通过信息
     */
    @RequestMapping("/nopassinfo/{id}")
    public R nopassinfo(@PathVariable("id") Integer id){

        ShopjoinEntity shopjoinEntity = shopjoinService.getById(id);
        //1.非专家不可以投票(用显示控制)
        //2.总共3个专家，若当前项目投票数少于3，则还有专家没投票
        if(shopjoinTpService.count(new QueryWrapper<ShopjoinTpEntity>().eq("uid",shopjoinEntity.getUid()))<3) {
            //判断当前专家对于该项目是否投过票，若没投就投
            if (shopjoinTpService.getOne(new QueryWrapper<ShopjoinTpEntity>().eq("uid", shopjoinEntity.getUid()).eq("name", ShiroUtils.getUserEntity().getUsername())) == null) {
                ShopjoinTpEntity shopjoinTpEntity = new ShopjoinTpEntity();
                shopjoinTpEntity.setName(ShiroUtils.getUserEntity().getUsername());
                shopjoinTpEntity.setResult(0);
                shopjoinTpEntity.setUid(shopjoinEntity.getUid());
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                int time = Integer.parseInt(formatter.format(date));
                shopjoinTpEntity.setTime(time + "");
                shopjoinTpService.save(shopjoinTpEntity);
                //投完票看是否该项目已经投完
                if (shopjoinTpService.count(new QueryWrapper<ShopjoinTpEntity>().eq("uid", shopjoinEntity.getUid())) == 3) {
                    List<ShopjoinTpEntity> list = shopjoinTpService.list(new QueryWrapper<ShopjoinTpEntity>().eq("uid", shopjoinEntity.getUid()));
                    int result = 0;
                    result += list.get(0).getResult();
                    result += list.get(1).getResult();
                    result += list.get(2).getResult();
                    if (result >= 2) {
                        shopjoinEntity.setTpResult("通过");
                        shopjoinEntity.setZztmc("专家已投票，投票通过");shopjoinEntity.setZztdm(6);
                        shopjoinService.updateById(shopjoinEntity);
                    } else {
                        shopjoinEntity.setTpResult("投票未通过");
                        shopjoinEntity.setZztmc("专家已投票，投票未通过");shopjoinEntity.setZztdm(4);
                        //删除之前的投票信息，重新投票
                        for(int i = 0; i < 3; i++){
                            shopjoinTpService.removeById(list.get(i).getId());
                        }
                        shopjoinService.updateById(shopjoinEntity);
                    }
                }
                return R.ok();
            } else {
                //重复专家投票
                return R.error("该专家已对该申请投过票了！");
            }
        }
        return R.ok();
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShopjoinTpEntity shopjoinTp){
		shopjoinTpService.save(shopjoinTp);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShopjoinTpEntity shopjoinTp){
		shopjoinTpService.updateById(shopjoinTp);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		shopjoinTpService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
