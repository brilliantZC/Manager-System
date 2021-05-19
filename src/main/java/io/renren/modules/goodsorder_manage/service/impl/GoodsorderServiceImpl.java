package io.renren.modules.goodsorder_manage.service.impl;

import org.springframework.stereotype.Service;
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
        IPage<GoodsorderEntity> page = this.page(
                new Query<GoodsorderEntity>().getPage(params),
                new QueryWrapper<GoodsorderEntity>()
        );

        return new PageUtils(page);
    }

}
