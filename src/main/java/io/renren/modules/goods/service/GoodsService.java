package io.renren.modules.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.goods.entity.GoodsEntity;

import java.util.Map;

/**
 * 商品管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-26 14:36:02
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

