package io.renren.modules.shopjoin_manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-03 10:00:22
 */
@Data
@TableName("shopjoin")
public class ShopjoinEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 负责人
	 */
	private String name;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 预开店地址
	 */
	private String address;
	/**
	 * 资金成本
	 */
	private String money;
	/**
	 * 营业许可
	 */
	private String yyxk;
	/**
	 * 营业许可状态代码
	 */
	private Integer yyxkztdm;
	/**
	 * 卫生许可
	 */
	private String wsxk;
	/**
	 * 卫生许可状态代码
	 */
	private Integer wsxkztdm;
	/**
	 * 实地考察结果
	 */
	private String addressResult;
	/**
	 * 个人信贷
	 */
	private String grxd;
	/**
	 * 个人信贷状态代码
	 */
	private Integer grxdztdm;
	/**
	 * 管理员投票结果
	 */
	private String tpResult;
	/**
	 * 最终审核结果
	 */
	private String finalResult;
	/**
	 * 审核时间
	 */
	private String finalTime;
	/**
	 * 总状态代码
	 */
	private Integer zztdm;
	/**
	 * 总状态名称
	 */
	private String zztmc;
	private Integer uid;

}
