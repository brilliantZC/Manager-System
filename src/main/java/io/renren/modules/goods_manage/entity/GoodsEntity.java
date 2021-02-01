package io.renren.modules.goods_manage.entity;

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
 * @date 2021-01-31 17:45:28
 */
@Data
@TableName("goods")
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品序号
	 */
	@TableId
	private Integer id;
	/**
	 * 商品名
	 */
	private String gname;
	/**
	 * 商品价格
	 */
	private Float gprice;
	/**
	 * 商品数量
	 */
	private Integer gcount;
	/**
	 * 商品图片
	 */
	private String gimage;
	/**
	 * 商品描述
	 */
	private String gintro;

}
