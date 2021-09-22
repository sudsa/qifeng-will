package com.hanxiaozhang.utils.exception;



/**
 * 〈一句话功能简述〉<br>
 * 〈自定义参数无效异常〉
 *
 * @author hanxinghua
 * @create 2019/7/10
 * @since 1.0.0
 */
public class ParameterInvalidException extends BusinessException {

	private static final long serialVersionUID = 3721036867889297081L;

	public ParameterInvalidException() {
		super();
	}

	public ParameterInvalidException(Object data) {
		super();
		super.data = data;
	}

	public ParameterInvalidException(ResultCode resultCode) {
		super(resultCode);
	}

	public ParameterInvalidException(ResultCode resultCode, Object data) {
		super(resultCode, data);
	}

	public ParameterInvalidException(String msg) {
		super(msg);
	}

	public ParameterInvalidException(String formatMsg, Object... objects) {
		super(formatMsg, objects);
	}

}
