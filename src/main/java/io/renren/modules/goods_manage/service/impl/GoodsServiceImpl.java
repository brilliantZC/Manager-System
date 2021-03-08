package io.renren.modules.goods_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.goods_manage.dao.GoodsDao;
import io.renren.modules.goods_manage.entity.GoodsEntity;
import io.renren.modules.goods_manage.service.GoodsService;


@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key=(String) params.get("key");
        IPage<GoodsEntity> page = this.page(
                new Query<GoodsEntity>().getPage(params),
                new QueryWrapper<GoodsEntity>().like("id",key).or().like("gname",key)
        );

        return new PageUtils(page);
    }

}
