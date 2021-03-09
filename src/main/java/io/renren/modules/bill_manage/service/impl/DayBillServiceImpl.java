package io.renren.modules.bill_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.bill_manage.dao.DayBillDao;
import io.renren.modules.bill_manage.entity.DayBillEntity;
import io.renren.modules.bill_manage.service.DayBillService;


@Service("dayBillService")
public class DayBillServiceImpl extends ServiceImpl<DayBillDao, DayBillEntity> implements DayBillService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key=(String) params.get("key");
        IPage<DayBillEntity> page = this.page(
                new Query<DayBillEntity>().getPage(params),
                new QueryWrapper<DayBillEntity>().like("id",key).or().like("day_billid",key)
        );

        return new PageUtils(page);
    }

}
