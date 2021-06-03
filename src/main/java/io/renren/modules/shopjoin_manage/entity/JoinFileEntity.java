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
 * @date 2021-06-03 18:56:15
 */
@Data
@TableName("joinfile")
public class JoinfileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 文件类型
	 */
	private String wjlx;
	/**
	 * 文件类型代码
	 */
	private String wjlxdm;
	/**
	 * 文件名称
	 */
	private String wjmc;
	/**
	 * 文件地址
	 */
	private String wjdz;
	/**
	 * 状态代码
	 */
	private String ztdm;
	/**
	 * 状态名称
	 */
	private String ztmc;
	/**
	 * uid
	 */
	private Integer uid;

}
