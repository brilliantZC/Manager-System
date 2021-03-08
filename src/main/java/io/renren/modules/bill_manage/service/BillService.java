package io.renren.modules.bill_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bill_manage.entity.BillEntity;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-08 20:23:36
 */
public interface BillService extends IService<BillEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

