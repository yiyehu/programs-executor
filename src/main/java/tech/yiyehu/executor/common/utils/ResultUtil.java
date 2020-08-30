package tech.yiyehu.executor.common.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class ResultUtil extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public ResultUtil() {
		put("code", 200);
		put("msg", "success");
	}
	
	public static ResultUtil error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static ResultUtil error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static ResultUtil error(int code, String msg) {
		ResultUtil resultUtil = new ResultUtil();
		resultUtil.put("code", code);
		resultUtil.put("msg", msg);
		return resultUtil;
	}

	public static ResultUtil ok(String msg) {
		ResultUtil resultUtil = new ResultUtil();
		resultUtil.put("msg", msg);
		return resultUtil;
	}
	
	public static ResultUtil ok(Map<String, Object> map) {
		ResultUtil resultUtil = new ResultUtil();
		resultUtil.putAll(map);
		return resultUtil;
	}
	
	public static ResultUtil ok() {
		return new ResultUtil();
	}

	public ResultUtil put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
