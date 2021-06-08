package io.renren.modules.shopjoin_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.shopjoin_manage.entity.ShopjoinTpEntity;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-08 20:54:40
 */
public interface ShopjoinTpService extends IService<ShopjoinTpEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

