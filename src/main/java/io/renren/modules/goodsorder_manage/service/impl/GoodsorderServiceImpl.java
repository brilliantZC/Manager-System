package io.renren.modules.goodsorder_manage.service.impl;

import io.renren.modules.bill_manage.entity.DayBillEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.goodsorder_manage.dao.GoodsorderDao;
import io.renren.modules.goodsorder_manage.entity.GoodsorderEntity;
import io.renren.modules.goodsorder_manage.service.GoodsorderService;


@Service("goodsorderService")
public class GoodsorderServiceImpl extends ServiceImpl<GoodsorderDao, GoodsorderEntity> implements GoodsorderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        IPage<GoodsorderEntity> page = this.page(
                new Query<GoodsorderEntity>().getPage(params),
                new QueryWrapper<GoodsorderEntity>().ne("zztdm",-1).and(i->i.like("name",key).or().like("phone",key).or().like("goods",key))
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils wxqueryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        IPage<GoodsorderEntity> page = this.page(
                new Query<GoodsorderEntity>().getPage(params),
                new QueryWrapper<GoodsorderEntity>().eq("zztdm",-1).and(i->i.like("name",key).or().like("phone",key).or().like("goods",key))
        );

        return new PageUtils(page);
    }

    @Override
    public List<GoodsorderEntity> selectAll() {
        return baseMapper.selectList(new QueryWrapper<GoodsorderEntity>().eq("zztdm",7));
    }
}
