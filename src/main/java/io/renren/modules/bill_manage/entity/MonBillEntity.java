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
 * @date 2021-03-10 14:49:14
 */
@Data
@TableName("mon_bill")
public class MonBillEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private Integer monMon;
	/**
	 *
	 */
	private Integer monYear;
	/**
	 *
	 */
	private Integer monBillid;
	/**
	 *
	 */
	private Float monIncome;
	/**
	 *
	 */
	private Float monOutcome;
	/**
	 *
	 */
	private Float monPure;
	/**
	 *
	 */
	private String monIntro;

}
