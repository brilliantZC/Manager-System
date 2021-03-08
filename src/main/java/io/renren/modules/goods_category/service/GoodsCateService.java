package io.renren.modules.goods_category.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.goods_category.entity.GoodsCateEntity;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-08 14:28:31
 */
public interface GoodsCateService extends IService<GoodsCateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

