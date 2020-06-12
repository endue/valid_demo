package com.simon.util;

import com.alibaba.fastjson.JSON;
import com.simon.annotation.Error;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @data: 2020/6/10 17:35
 * @author: simon
 * @version: 常用错误提示
 * @description:
 */
public class ErrorCode {

	private static final Map<String, String> CODE_MAP = new HashMap<>();

	private ErrorCode() {
	}

	static{
		load(ErrorCode.class);
	}

	public static synchronized void load(Class<?> clazz) {
		try {
			for (Field field : clazz.getFields()) {
				if (field.isAnnotationPresent(Error.class)) {
					Error error = field.getAnnotation(Error.class);
					Object code = null;
					if (error != null && (code = field.get(clazz)) != null) {
						CODE_MAP.put(code.toString(), error.msg());
					}
				}
			}
		} catch (Exception e) {

		}
	}

	@Error(msg = "操作成功")
	public static final String SUCCESS_CODE = "10000";

	@Error(msg = "系统异常")
	public static final String EXCEPTION_CODE = "11000";

	@Error(msg = "参数不能为空")
	public static final String EMPTY_CODE = "10001";

	@Error(msg = "字段太长")
	public static final String TOO_LONG_CODE = "10002";

	@Error(msg = "数据不合法")
	public static final String ERROR_CODE = "10013";

	public static String getErrorMessage(String errorCode){
		return CODE_MAP.get(errorCode);
	}


	public static String toErrorMessage(String errorCode, String field) {
		Map<String, String> map = new HashMap<>();
		StringBuilder sb = new StringBuilder(field).append(" ");
		map.put("code", errorCode);
		map.put("message", sb.append(ErrorCode.CODE_MAP.get(errorCode)).toString());
		return JSON.toJSONString(map);
	}
}
