package io.renren.modules.bill_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bill_manage.entity.MonBillEntity;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-10 14:49:14
 */
public interface MonBillService extends IService<MonBillEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPagemon(Map<String, Object> params);
}

