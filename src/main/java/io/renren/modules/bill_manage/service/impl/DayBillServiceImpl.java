package io.renren.modules.bill_manage.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<DayBillEntity> selectAll() {
        return baseMapper.selectList(new QueryWrapper<DayBillEntity>().like("day_year",2));
    }

    @Override
    public PageUtils queryPageday(Map<String, Object> params) {
        String key=(String) params.get("key");
        String key1=(String) params.get("key1");
        IPage<DayBillEntity> page = this.page(
                new Query<DayBillEntity>().getPage(params),
                new QueryWrapper<DayBillEntity>().eq("day_mon",key).eq("day_year",key1)
        );

        return new PageUtils(page);
    }


}
