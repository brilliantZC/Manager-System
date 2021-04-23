package io.renren.modules.supply_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.supply_manage.entity.WjsaveEntity;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-04-23 09:54:11
 */
public interface WjsaveService extends IService<WjsaveEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

