package io.renren.modules.goods_category.entity;

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
 * @date 2021-03-08 14:28:31
 */
@Data
@TableName("goods_cate")
public class GoodsCateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private String cateName;
	/**
	 *
	 */
	private Integer cateNum;
	/**
	 *
	 */
	private String cateIntro;

}
