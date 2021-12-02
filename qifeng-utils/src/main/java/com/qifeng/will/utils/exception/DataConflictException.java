package com.qifeng.will.utils.exception;





/**
 * 〈一句话功能简述〉<br>
 * 〈自定义数据已经存在异常〉
 *
 * @author hanxinghua
 * @create 2019/7/10
 * @since 1.0.0
 */
public class DataConflictException extends BusinessException {

	private static final long serialVersionUID = 3721036867889297081L;

	public DataConflictException() {
		super();
	}

	public DataConflictException(Object data) {
		super.data = data;
	}

	public DataConflictException(ResultCode resultCode) {
		super(resultCode);
	}

	public DataConflictException(ResultCode resultCode, Object data) {
		super(resultCode, data);
	}

	public DataConflictException(String msg) {
		super(msg);
	}

	public DataConflictException(String formatMsg, Object... objects) {
		super(formatMsg, objects);
	}


}
