package io.renren.modules.supply_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.supply_manage.entity.GywjbEntity;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-22 21:24:14
 */
public interface GywjbService extends IService<GywjbEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils gyqueryPage(Map<String, Object> params);

}

