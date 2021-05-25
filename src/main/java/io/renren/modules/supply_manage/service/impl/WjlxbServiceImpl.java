package io.renren.modules.supply_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.supply_manage.dao.WjlxbDao;
import io.renren.modules.supply_manage.entity.WjlxbEntity;
import io.renren.modules.supply_manage.service.WjlxbService;


@Service("wjlxbService")
public class WjlxbServiceImpl extends ServiceImpl<WjlxbDao, WjlxbEntity> implements WjlxbService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        IPage<WjlxbEntity> page = this.page(
                new Query<WjlxbEntity>().getPage(params),
                new QueryWrapper<WjlxbEntity>().like("jdmc",key).or().like("wjlxdm",key).or().like("wjlxmc",key)
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils fbqueryPage(Map<String, Object> params) {
        IPage<WjlxbEntity> page = this.page(
                new Query<WjlxbEntity>().getPage(params),
                new QueryWrapper<WjlxbEntity>().eq("jddm","1")
        );

        return new PageUtils(page);
    }

}
