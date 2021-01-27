package io.renren.modules.goods.dao;

import io.renren.modules.goods.entity.GoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-26 14:36:02
 */
@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {

}
