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
 * @date 2021-04-23 09:54:11
 */
@Data
@TableName("wjsave")
public class WjsaveEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private String wjlx;
	/**
	 *
	 */
	private String wjdm;
	/**
	 *
	 */
	private String wjmc;
	/**
	 *
	 */
	private String wjlxdm;
	/**
	 *
	 */
	private Integer ztdm;
	/**
	 *
	 */
	private String ztmc;
	/**
	 *
	 */
	private Integer uid;
	/**
	 *
	 */
	private String wjdz;

}
