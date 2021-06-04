package io.renren.modules.shopjoin_manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.shopjoin_manage.dao.JoinfileDao;
import io.renren.modules.shopjoin_manage.entity.JoinfileEntity;
import io.renren.modules.shopjoin_manage.service.JoinfileService;


@Service("joinfileService")
public class JoinfileServiceImpl extends ServiceImpl<JoinfileDao, JoinfileEntity> implements JoinfileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<JoinfileEntity> page = this.page(
                new Query<JoinfileEntity>().getPage(params),
                new QueryWrapper<JoinfileEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils detailqueryPage(Map<String, Object> params) {
        int uid= Integer.parseInt((String) params.get("key"));
        IPage<JoinfileEntity> page = this.page(
                new Query<JoinfileEntity>().getPage(params),
                new QueryWrapper<JoinfileEntity>().eq("uid",uid)
        );

        return new PageUtils(page);
    }

}
