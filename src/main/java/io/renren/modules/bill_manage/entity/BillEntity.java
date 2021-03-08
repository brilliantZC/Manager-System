package io.renren.modules.bill_manage.entity;

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
 * @date 2021-03-08 20:23:36
 */
@Data
@TableName("bill")
public class BillEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private Integer billId;
	/**
	 *
	 */
	private String billName;
	/**
	 *
	 */
	private Float billAccount;

}
