package com.hanxiaozhang.utils.exception;




/**
 * 〈一句话功能简述〉<br>
 * 〈自定义远程访问异常〉
 *
 * @author hanxinghua
 * @create 2019/7/10
 * @since 1.0.0
 */
public class RemoteAccessException extends BusinessException {

	private static final long serialVersionUID = -832464574076215195L;

	public RemoteAccessException() {
		super();
	}

	public RemoteAccessException(Object data) {
		super.data = data;
	}

	public RemoteAccessException(ResultCode resultCode) {
		super(resultCode);
	}

	public RemoteAccessException(ResultCode resultCode, Object data) {
		super(resultCode, data);
	}

	public RemoteAccessException(String msg) {
		super(msg);
	}

	public RemoteAccessException(String formatMsg, Object... objects) {
		super(formatMsg, objects);
	}

}
