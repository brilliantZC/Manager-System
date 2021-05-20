package io.renren.modules.goodsorder_manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-05-19 15:08:04
 */
@Data
@TableName("goodsorder")
public class GoodsorderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 选购商品
	 */
	private String goods;
	/**
	 * 价格
	 */
	private Float price;
	/**
	 * 订单创建时间
	 */
	private String timeStar;
	/**
	 * 订单持续时间
	 */
	private String timeStay;
	/**
	 * 总状态代码
	 */
	private Integer zztdm;
	/**
	 * 总状态名称
	 */
	private String zztmc;
	/**
	 * 订单描述
	 */
	private String intro;

}
