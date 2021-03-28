package io.renren.modules.supply_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.supply_manage.dao.GywjbDao;
import io.renren.modules.supply_manage.entity.GywjbEntity;
import io.renren.modules.supply_manage.service.GywjbService;


@Service("gywjbService")
public class GywjbServiceImpl extends ServiceImpl<GywjbDao, GywjbEntity> implements GywjbService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key=(String) params.get("key");
        IPage<GywjbEntity> page = this.page(
                new Query<GywjbEntity>().getPage(params),
                new QueryWrapper<GywjbEntity>().eq("uid",key)
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils gyqueryPage(Map<String, Object> params) {

        IPage<GywjbEntity> page = this.page(
                new Query<GywjbEntity>().getPage(params),
                new QueryWrapper<GywjbEntity>().eq("wjlxdm","AQS")
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils xgqueryPage(Map<String, Object> params) {
        int key=(Integer) params.get("key");
        IPage<GywjbEntity> page = this.page(
                new Query<GywjbEntity>().getPage(params),
                new QueryWrapper<GywjbEntity>().eq("uid",key)
        );

        return new PageUtils(page);
    }


}
