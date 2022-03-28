/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.excel.process;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author fengcheng
 * @version 2019-06-15
 */
@Slf4j
@SpringBootApplication
public class ExcelProcessHandle {

	public static String inPath = "exec/过程数据.xlsx";
	public static String outPath = "exec/根据过程数据生成的结果数据.xlsx";
	public static String mode = "y";

	public static void main(String[] args) {
		if (args.length < 2) {
			log.error("缺少文件路径参数");
			return;
		}
		inPath = args[0];
		outPath = args[1];
		if (args.length == 3) {
			mode = args[2];
		}

		ExcelReader excelReader = null;
		try {
			excelReader = EasyExcel.read(inPath).build();
			ReadSheet readSheet = readSheet(0, ProcessData.class);
			excelReader.read(readSheet);
		} finally {
			if (excelReader != null) {
				excelReader.finish();
			}
		}

	}

	private static <T> ReadSheet readSheet(int i, Class<T> valueType) {
		return EasyExcel.readSheet(i).head(valueType).headRowNumber(2).registerReadListener(new ExcelProcessListener())
				.build();
	}
}
