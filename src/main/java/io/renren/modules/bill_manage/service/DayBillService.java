package io.renren.modules.bill_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bill_manage.entity.DayBillEntity;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-10 10:31:52
 */
public interface DayBillService extends IService<DayBillEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPageday(Map<String, Object> params);
}

