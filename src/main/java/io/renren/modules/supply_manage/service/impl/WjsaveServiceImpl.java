package io.renren.modules.supply_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.supply_manage.dao.WjsaveDao;
import io.renren.modules.supply_manage.entity.WjsaveEntity;
import io.renren.modules.supply_manage.service.WjsaveService;


@Service("wjsaveService")
public class WjsaveServiceImpl extends ServiceImpl<WjsaveDao, WjsaveEntity> implements WjsaveService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WjsaveEntity> page = this.page(
                new Query<WjsaveEntity>().getPage(params),
                new QueryWrapper<WjsaveEntity>()
        );

        return new PageUtils(page);
    }

}
