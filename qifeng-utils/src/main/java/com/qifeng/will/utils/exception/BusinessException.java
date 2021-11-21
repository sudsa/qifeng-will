package com.hanxiaozhang.utils.exception;


import com.hanxiaozhang.utils.StringUtil;
import lombok.Data;


/**
 * 〈一句话功能简述〉<br>
 * 〈自定义业务异常类〉
 *   参考：https://blog.csdn.net/aiyaya_/article/details/78989226
 *
 * @author hanxinghua
 * @create 2019/7/10
 * @since 1.0.0
 */

@Data
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 194906846739586856L;

	protected String code;

	protected String message;

	protected ResultCode resultCode;

	protected Object data;

	public BusinessException() {
		BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.getByEClass(this.getClass());
		if (exceptionEnum != null) {
			resultCode = exceptionEnum.getResultCode();
			code = exceptionEnum.getResultCode().code().toString();
			message = exceptionEnum.getResultCode().message();
		}

	}

	public BusinessException(String message) {
		this();
		this.message = message;
	}

	public BusinessException(String format, Object... objects) {
		this();
		this.message = StringUtil.formatIfArgs(format, "{}", objects);
	}

	public BusinessException(ResultCode resultCode, Object data) {
		this(resultCode);
		this.data = data;
	}

	public BusinessException(ResultCode resultCode) {
		this.resultCode = resultCode;
		this.code = resultCode.code().toString();
		this.message = resultCode.message();
	}

}
