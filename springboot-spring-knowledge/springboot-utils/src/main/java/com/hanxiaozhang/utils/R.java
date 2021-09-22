package com.hanxiaozhang.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈R〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/18
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", 0);
		put("msg", "操作成功");
	}

	public static R ok() {
		return new R();
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}


	public static R error() {
		return error(1, "操作失败");
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R error(String msg) {
		return error(1, msg);
	}


	public static R error(Map<String, Object> map) {
		R r = new R();
		r.put("code", 1);
		r.put("map", map);
		r.put("msg", "操作失败");
		return r;
	}
	public static R error(int code, Map<String, Object> map,String msg) {
		R r = new R();
		r.put("code", code);
		r.put("map", map);
		r.put("msg", msg);
		return r;
	}


	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
