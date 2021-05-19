package io.renren.modules.goodsorder_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.goodsorder_manage.entity.GoodsorderEntity;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-05-19 15:08:04
 */
public interface GoodsorderService extends IService<GoodsorderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

