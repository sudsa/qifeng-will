package com.hanxiaozhang.utils.exception;




/**
 * 〈一句话功能简述〉<br>
 * 〈自定义权限不足异常〉
 *
 * @author hanxinghua
 * @create 2019/7/10
 * @since 1.0.0
 */
public class PermissionForbiddenException extends BusinessException {

	private static final long serialVersionUID = 3721036867889297081L;

	public PermissionForbiddenException() {
		super();
	}

	public PermissionForbiddenException(Object data) {
		super.data = data;
	}

	public PermissionForbiddenException(ResultCode resultCode) {
		super(resultCode);
	}

	public PermissionForbiddenException(ResultCode resultCode, Object data) {
		super(resultCode, data);
	}

	public PermissionForbiddenException(String msg) {
		super(msg);
	}

	public PermissionForbiddenException(String formatMsg, Object... objects) {
		super(formatMsg, objects);
	}

}
