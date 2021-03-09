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
 * @date 2021-03-09 16:04:03
 */
public interface BillService extends IService<BillEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

