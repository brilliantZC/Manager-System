package io.renren.modules.shopjoin_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.shopjoin_manage.dao.ShopjoinTpDao;
import io.renren.modules.shopjoin_manage.entity.ShopjoinTpEntity;
import io.renren.modules.shopjoin_manage.service.ShopjoinTpService;


@Service("shopjoinTpService")
public class ShopjoinTpServiceImpl extends ServiceImpl<ShopjoinTpDao, ShopjoinTpEntity> implements ShopjoinTpService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShopjoinTpEntity> page = this.page(
                new Query<ShopjoinTpEntity>().getPage(params),
                new QueryWrapper<ShopjoinTpEntity>()
        );

        return new PageUtils(page);
    }

}
