package io.renren.modules.shopjoin_manage.entity;

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
 * @date 2021-06-08 20:54:40
 */
@Data
@TableName("shopjoin_tp")
public class ShopjoinTpEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 投票人
	 */
	private String name;
	/**
	 * 投票时间
	 */
	private String time;
	/**
	 * uid
	 */
	private Integer uid;
	/**
	 * 投票结果
	 */
	private Integer result;

}
