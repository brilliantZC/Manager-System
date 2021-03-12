package io.renren.modules.bill_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.bill_manage.dao.MonBillDao;
import io.renren.modules.bill_manage.entity.MonBillEntity;
import io.renren.modules.bill_manage.service.MonBillService;


@Service("monBillService")
public class MonBillServiceImpl extends ServiceImpl<MonBillDao, MonBillEntity> implements MonBillService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key=(String) params.get("key");
        IPage<MonBillEntity> page = this.page(
                new Query<MonBillEntity>().getPage(params),
                new QueryWrapper<MonBillEntity>().like("id",key).or().like("mon_billid",key)
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPagemon(Map<String, Object> params) {
        String key=(String) params.get("key");
        String key1=(String) params.get("key");
        IPage<MonBillEntity> page = this.page(
                new Query<MonBillEntity>().getPage(params),
                new QueryWrapper<MonBillEntity>().like("mon_mon",key)
                        .and(wrapper->wrapper.like("mon_year",key1))
        );

        return new PageUtils(page);
    }

}
