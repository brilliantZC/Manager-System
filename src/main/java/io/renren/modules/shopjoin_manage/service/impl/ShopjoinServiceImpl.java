package io.renren.modules.shopjoin_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.shopjoin_manage.dao.ShopjoinDao;
import io.renren.modules.shopjoin_manage.entity.ShopjoinEntity;
import io.renren.modules.shopjoin_manage.service.ShopjoinService;


@Service("shopjoinService")
public class ShopjoinServiceImpl extends ServiceImpl<ShopjoinDao, ShopjoinEntity> implements ShopjoinService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShopjoinEntity> page = this.page(
                new Query<ShopjoinEntity>().getPage(params),
                new QueryWrapper<ShopjoinEntity>()
        );

        return new PageUtils(page);
    }

}
