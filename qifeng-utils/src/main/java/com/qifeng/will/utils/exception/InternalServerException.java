package com.qifeng.will.utils.exception;


/**
 * 〈一句话功能简述〉<br>
 * 〈自定义内部服务异常〉
 *
 * @author hanxinghua
 * @create 2019/7/10
 * @since 1.0.0
 */
public class InternalServerException extends BusinessException {

	private static final long serialVersionUID = 2659909836556958676L;

	public InternalServerException() {
		super();
	}

	public InternalServerException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InternalServerException(String msg, Throwable cause, Object... objects) {
		super(msg, cause, objects);
	}

	public InternalServerException(String msg) {
		super(msg);
	}

	public InternalServerException(String formatMsg, Object... objects) {
		super(formatMsg, objects);
	}

}
