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
@TableName("gywjb")
public class GywjbEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 发布人
	 */
	private String name;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 材料名
	 */
	private String materialName;
	/**
	 * 材料数量
	 */
	private String materialNum;
	/**
	 * 材料价格
	 */
	private Float materialPrice;
	/**
	 * 简介
	 */
	private String intro;
	/**
	 * 状态代码
	 */
	private Integer ztdm;
	/**
	 * 状态名称
	 */
	private String ztmc;
	/**
	 * 开始日期
	 */
	private String ksri;
	/**
	 * 结束日期
	 */
	private String jsrq;
	/**
	 * 文件地址
	 */
	private String wjdz;
	/**
	 * 文件名称
	 */
	private String wjmc;
	/**
	 * 送货方式
	 */
	private String shfs;
	/**
	 * 文件类型名称
	 */
	private String wjlxmc;
	/**
	 * 文件类型代码
	 */
	private String wjlxdm;

}
