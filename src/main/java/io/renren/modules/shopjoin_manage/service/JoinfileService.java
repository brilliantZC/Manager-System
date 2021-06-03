package io.renren.modules.shopjoin_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.shopjoin_manage.entity.JoinfileEntity;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-03 18:56:15
 */
public interface JoinfileService extends IService<JoinfileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

