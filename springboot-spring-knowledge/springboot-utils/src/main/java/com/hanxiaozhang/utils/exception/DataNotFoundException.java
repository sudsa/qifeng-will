package com.hanxiaozhang.utils.exception;



/**
 * 〈一句话功能简述〉<br>
 * 〈自定义数据没有找到异常〉
 *
 * @author hanxinghua
 * @create 2019/7/10
 * @since 1.0.0
 */
public class DataNotFoundException extends BusinessException {

	private static final long serialVersionUID = 3721036867889297081L;

	public DataNotFoundException() {
		super();
	}

	public DataNotFoundException(Object data) {
		super();
		super.data = data;
	}

	public DataNotFoundException(ResultCode resultCode) {
		super(resultCode);
	}

	public DataNotFoundException(ResultCode resultCode, Object data) {
		super(resultCode, data);
	}

	public DataNotFoundException(String msg) {
		super(msg);
	}

	public DataNotFoundException(String formatMsg, Object... objects) {
		super(formatMsg, objects);
	}

}
