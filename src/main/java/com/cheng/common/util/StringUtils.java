package com.cheng.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 *
 * @author fengcheng
 * @version 2016-01-15
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static final char SEPARATOR = '_';
	private static final Pattern PATTERN = Pattern.compile("<([a-zA-Z]+)[^<>]*>");

	/**
	 * 转换为字节数组
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			return str.getBytes(StandardCharsets.UTF_8);
		} else {
			return new byte[0];
		}
	}

	/**
	 * 转换为字节数组
	 */
	public static String toString(byte[] bytes) {
		return new String(bytes, StandardCharsets.UTF_8);
	}

	/**
	 * 是否包含字符串
	 *
	 * @param str  验证字符串
	 * @param strs 字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs) {
		if (str != null) {
			for (String s : strs) {
				if (str.equals(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}

	public static String replaceHeadHtml(String html) {
		if (isBlank(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("").trim();
		return html.substring(html.indexOf(s.charAt(0)));
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 */
	public static String replaceMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}

	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val) {
		if (val == null) {
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val) {
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val) {
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val) {
		return toLong(val).intValue();
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase(" hello_world ") == "helloWorld"
	 * toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase(" hello_world ") == "helloWorld"
	 * toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 *
	 * @return toCamelCase(" hello_world ") == "helloWorld"
	 * toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}
		return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), "_").toLowerCase();
	}

	/**
	 * 转换为JS获取对象值，生成三目运算返回结果
	 *
	 * @param objectString 对象串
	 *                     例如：row.user.id
	 *                     返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
	 */
	public static String jsGetVal(String objectString) {
		StringBuilder result = new StringBuilder();
		StringBuilder val = new StringBuilder();
		String[] vals = split(objectString, ".");
		for (String s : vals) {
			val.append(".").append(s);
			result.append("!").append(val.substring(1)).append("?'':");
		}
		result.append(val.substring(1));
		return result.toString();
	}

	/**
	 * 是否大写字母
	 */
	public static boolean isUppercaseAlpha(char c) {
		return (c >= 'A') && (c <= 'Z');
	}

	/**
	 * 是否小写字母
	 */
	public static boolean isLowercaseAlpha(char c) {
		return (c >= 'a') && (c <= 'z');
	}

	/**
	 * Not equals
	 */
	public static boolean ne(String s1, String s2) {
		return !equals(s1, s2);
	}

	/**
	 * equals
	 */
	public static boolean eq(String s1, String s2) {
		return equals(s1, s2);
	}

	/**
	 * equals Non Null
	 */
	public static boolean equalsNonNull(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return false;
		}
		return equals(s1, s2);
	}

	/**
	 * 字符串拼接连续添加
	 * @param sb 已存在的StringBuffer对象，可以为null
	 * @param objects 拼接对象
	 * @return 拼接过后的字符串
	 */
	public static String append2String(StringBuilder sb, Object... objects) {
		return append(sb, objects).toString();
	}

	/**
	 * 字符串拼接连续添加
	 * @param objects 拼接对象
	 * @return 拼接过后的字符串
	 */
	public static String append2String(Object... objects) {
		return append(objects).toString();
	}

	/**
	 * 字符串拼接连续添加
	 * @param sb 已存在的StringBuffer对象，可以为null
	 * @param objects 拼接对象
	 * @return 拼接过后的StringBuffer对象
	 */
	public static StringBuilder append(StringBuilder sb, Object... objects) {
		if (null == sb) {
			sb = new StringBuilder();
		}
		for (Object o : objects) {
			sb.append(null == o ? "" : o);
		}
		return sb;
	}

	/**
	 * 字符串拼接连续添加
	 * @param objects 拼接对象
	 * @return 拼接过后的StringBuffer对象
	 */
	public static StringBuilder append(Object... objects) {
		return append(null, objects);
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
