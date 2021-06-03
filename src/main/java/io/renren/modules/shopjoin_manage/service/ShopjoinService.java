package io.renren.modules.shopjoin_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.shopjoin_manage.entity.ShopjoinEntity;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-03 10:00:22
 */
public interface ShopjoinService extends IService<ShopjoinEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

