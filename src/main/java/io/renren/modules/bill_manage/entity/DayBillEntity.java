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
 * @date 2021-03-10 10:31:52
 */
@Data
@TableName("day_bill")
public class DayBillEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private Integer dayDay;
	/**
	 *
	 */
	private Integer dayMon;
	/**
	 *
	 */
	private Integer dayYear;
	/**
	 *
	 */
	private String dayWeek;
	/**
	 *
	 */
	private Float dayIncome;
	/**
	 *
	 */
	private Float dayOutcome;
	/**
	 *
	 */
	private Integer dayBillid;
	/**
	 *
	 */
	private Float dayPure;

}
