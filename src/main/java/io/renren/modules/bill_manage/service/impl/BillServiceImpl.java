package io.renren.modules.bill_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.bill_manage.dao.BillDao;
import io.renren.modules.bill_manage.entity.BillEntity;
import io.renren.modules.bill_manage.service.BillService;


@Service("billService")
public class BillServiceImpl extends ServiceImpl<BillDao, BillEntity> implements BillService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key=(String) params.get("key");
        IPage<BillEntity> page = this.page(
                new Query<BillEntity>().getPage(params),
                new QueryWrapper<BillEntity>().like("id",key).or().like("day_billid",key)
        );

        return new PageUtils(page);
    }

}
