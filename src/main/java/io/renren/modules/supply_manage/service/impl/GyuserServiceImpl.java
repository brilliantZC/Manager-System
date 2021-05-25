package io.renren.modules.supply_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.supply_manage.dao.GyuserDao;
import io.renren.modules.supply_manage.entity.GyuserEntity;
import io.renren.modules.supply_manage.service.GyuserService;


@Service("gyuserService")
public class GyuserServiceImpl extends ServiceImpl<GyuserDao, GyuserEntity> implements GyuserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        IPage<GyuserEntity> page = this.page(
                new Query<GyuserEntity>().getPage(params),
                new QueryWrapper<GyuserEntity>().like("gyname",key).or().like("gyphone",key)
        );

        return new PageUtils(page);
    }

}
