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
@TableName("wjlxb")
public class WjlxbEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 进度名称
	 */
	private String jdmc;
	/**
	 * 进度代码
	 */
	private String jddm;
	/**
	 * 类型名称
	 */
	private String wjlxmc;
	/**
	 * 类型代码
	 */
	private String wjlxdm;

}
