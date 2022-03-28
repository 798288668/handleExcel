/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.excel.process;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.cheng.common.util.Reflections;
import com.cheng.excel.ColumnWidthHandle;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author fengcheng
 * @version 2019-06-15
 */
@Slf4j
@SuppressWarnings("unchecked")
public class ExcelProcessListener extends AnalysisEventListener {

	private static final List<ProcessData> processData = new ArrayList<>();
	private static final Set<String> project = new LinkedHashSet<>(64);

	@Override
	public void invokeHeadMap(Map headMap, AnalysisContext context) {
		if (context.readRowHolder().getRowIndex() == 0) {
			headMap.forEach((k, v) -> {
				if ((int) k >= 5) {
					project.add(v.toString());
				}
			});
			System.out.println(project);
		}
	}

	@Override
	public void invoke(Object object, AnalysisContext context) {

		processData.add((ProcessData) object);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		BigDecimal costSum = BigDecimal.ZERO;
		Map<String, Map<String, BigDecimal>> categorySum = new HashMap<>(processData.size());
		ArrayList<String> projectList = new ArrayList<>(project);
		projectList.forEach(e -> categorySum.put(e, new HashMap<>(64)));
		processData.sort(Comparator.comparing(ProcessData::getCostDate));
		for (ProcessData processData : processData) {
			costSum = costSum.add(processData.getCost());
			for (int i = 0; i < projectList.size(); i++) {
				Map<String, BigDecimal> key = categorySum.get(projectList.get(i));
				BigDecimal cast = key.get(processData.getCategory());
				if (cast == null) {
					cast = BigDecimal.ZERO;
				}
				Object money = Reflections.invokeGetter(processData, "money" + (i + 1));
				key.put(processData.getCategory(), cast.add(new BigDecimal(money.toString())));
			}
		}
		writerExcleResult(categorySum);
	}


	private void writerExcleResult(Map<String, Map<String, BigDecimal>> categorySum) {
		// 动态添加 表头 headList --> 所有表头行集合
		List<List<String>> headList = new ArrayList<>();
		List<String> headTitle = new ArrayList<>();
		headTitle.add("序号");
		headTitle.add("序号");
		headList.add(headTitle);

		headTitle = new ArrayList<>();
		headTitle.add("项目名称");
		headTitle.add("项目名称");
		headList.add(headTitle);

		headTitle = new ArrayList<>();
		headTitle.add("开始时间");
		headTitle.add("开始时间");
		headList.add(headTitle);

		headTitle = new ArrayList<>();
		headTitle.add("结束时间");
		headTitle.add("结束时间");
		headList.add(headTitle);

		headTitle = new ArrayList<>();
		headTitle.add("人数");
		headTitle.add("人数");
		headList.add(headTitle);

		int categoryCount = 1;
		String anyKey = categorySum.keySet().stream().findAny().orElse(null);
		for (String key : categorySum.get(anyKey).keySet()) {
			headTitle = new ArrayList<>();
			headTitle.add("分配结果");
			headTitle.add(key);
			headList.add(headTitle);
			categoryCount++;
		}

		headTitle = new ArrayList<>();
		headTitle.add("合计");
		headTitle.add("合计");
		headList.add(headTitle);

		List<List<Object>> datas = new ArrayList<>();
		project.forEach(e -> {
			List<Object> data = new ArrayList<>();
			data.add(null);
			data.add(e);
			data.add(null);
			data.add(null);
			data.add(null);
			BigDecimal sum = BigDecimal.ZERO;
			for (Map.Entry<String, BigDecimal> decimalEntry : categorySum.get(e).entrySet()) {
				data.add(decimalEntry.getValue());
				sum = sum.add(decimalEntry.getValue());
			}
			data.add(sum);
			datas.add(data);
		});

		// 最后一行的合计
		List<Object> data = new ArrayList<>();
		data.add("合计");
		data.add(null);
		data.add(null);
		data.add(null);
		data.add(null);

		List<List<Object>> currentDatas = new ArrayList<>(datas);
		int startColumn = 5;
		for (int i = 0; i < categoryCount; i++) {
			BigDecimal columnSum = BigDecimal.ZERO;
			for (List<Object> objects : currentDatas) {
				columnSum = columnSum.add(new BigDecimal(objects.get(startColumn).toString()));
			}
			data.add(columnSum);
			startColumn++;
		}
		datas.add(data);

		EasyExcel.write(ExcelProcessHandle.outPath)
				// sheet
				.sheet("分配结果")
				// head
				.head(headList).registerWriteHandler(new ColumnWidthHandle()).doWrite(datas);
	}


	/**
	 * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
	 */
	@Override
	public void onException(Exception exception, AnalysisContext context) throws Exception {
		log.error("解析失败，{}", exception.getMessage());
		// 如果是某一个单元格的转换异常 能获取到具体行号
		// 如果要获取头的信息 配合invokeHeadMap使用
		if (exception instanceof ExcelDataConvertException) {
			ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
			log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
					excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
		}
		if (!exception.getMessage().contains("合计")) {
			throw exception;
		}
	}
}
