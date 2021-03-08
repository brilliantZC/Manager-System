package io.renren.modules.goods_category.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.goods_category.dao.GoodsCateDao;
import io.renren.modules.goods_category.entity.GoodsCateEntity;
import io.renren.modules.goods_category.service.GoodsCateService;


@Service("goodsCateService")
public class GoodsCateServiceImpl extends ServiceImpl<GoodsCateDao, GoodsCateEntity> implements GoodsCateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key=(String) params.get("key");
        IPage<GoodsCateEntity> page = this.page(
                new Query<GoodsCateEntity>().getPage(params),
                new QueryWrapper<GoodsCateEntity>().like("id",key).or().like("cate_name",key)
        );

        return new PageUtils(page);
    }

}
