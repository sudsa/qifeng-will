package com.qifeng.will.utils.exception;


/**
 * 〈一句话功能简述〉<br>
 * 〈自定义用户未登录异常〉
 *
 * @author howill.zou
 * @create 2019/7/10
 * @since 1.0.0
 */
public class UserNotLoginException extends BusinessException {

	private static final long serialVersionUID = -1879503946782379204L;

	public UserNotLoginException() {
		super();
	}

	public UserNotLoginException(String msg) {
		super(msg);
	}

	public UserNotLoginException(String formatMsg, Object... objects) {
		super(formatMsg, objects);
	}

}
