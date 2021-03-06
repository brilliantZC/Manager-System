package io.renren.modules.supply_manage.entity;

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
 * @date 2021-03-22 21:24:14
 */
@Data
@TableName("gyuser")
public class GyuserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private String gyname;
	/**
	 *
	 */
	private String gyphone;
	/**
	 *
	 */
	private String gyaddress;
	/**
	 *
	 */
	private Integer gycount;
	/**
	 *
	 */
	private Integer flag;

}
