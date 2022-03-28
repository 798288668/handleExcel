/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.excel.process;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author fengcheng
 * @version 2019-06-15
 */
@Data
public class ProcessData {

	/**
	 * 日期
	 */
	@ExcelProperty
	private Date costDate;

	/**
	 * 分类
	 */
	@ExcelProperty
	private String no;
	/**
	 * 分类
	 */
	@ExcelProperty
	private String summary;
	/**
	 * 分类
	 */
	@ExcelProperty
	private String category;

	/**
	 * 金额
	 */
	@ExcelProperty
	private BigDecimal cost;

	@ExcelProperty
	private BigDecimal rate1;
	@ExcelProperty
	private BigDecimal money1;

	@ExcelProperty
	private BigDecimal rate2;
	@ExcelProperty
	private BigDecimal money2;

	@ExcelProperty
	private BigDecimal rate3;
	@ExcelProperty
	private BigDecimal money3;

	@ExcelProperty
	private BigDecimal rate4;
	@ExcelProperty
	private BigDecimal money4;

	@ExcelProperty
	private BigDecimal rate5;
	@ExcelProperty
	private BigDecimal money5;

	@ExcelProperty
	private BigDecimal rate6;
	@ExcelProperty
	private BigDecimal money6;

	@ExcelProperty
	private BigDecimal rate7;
	@ExcelProperty
	private BigDecimal money7;

	@ExcelProperty
	private BigDecimal rate8;
	@ExcelProperty
	private BigDecimal money8;

	@ExcelProperty
	private BigDecimal rate9;
	@ExcelProperty
	private BigDecimal money9;

	@ExcelProperty
	private BigDecimal rate10;
	@ExcelProperty
	private BigDecimal money10;

	@ExcelProperty
	private BigDecimal rate11;
	@ExcelProperty
	private BigDecimal money11;

	@ExcelProperty
	private BigDecimal rate12;
	@ExcelProperty
	private BigDecimal money12;

	@ExcelProperty
	private BigDecimal rate13;
	@ExcelProperty
	private BigDecimal money13;

	@ExcelProperty
	private BigDecimal rate14;
	@ExcelProperty
	private BigDecimal money14;

	@ExcelProperty
	private BigDecimal rate15;
	@ExcelProperty
	private BigDecimal money15;

	@ExcelProperty
	private BigDecimal rate16;
	@ExcelProperty
	private BigDecimal money16;

	@ExcelProperty
	private BigDecimal rate17;
	@ExcelProperty
	private BigDecimal money17;

	@ExcelProperty
	private BigDecimal rate18;
	@ExcelProperty
	private BigDecimal money18;

	@ExcelProperty
	private BigDecimal rate19;
	@ExcelProperty
	private BigDecimal money19;

	@ExcelProperty
	private BigDecimal rate20;
	@ExcelProperty
	private BigDecimal money20;
}
