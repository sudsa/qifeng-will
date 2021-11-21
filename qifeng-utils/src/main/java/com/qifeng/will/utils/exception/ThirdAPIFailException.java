package com.hanxiaozhang.utils.exception;


/**
 * 〈一句话功能简述〉<br>
 * 〈自定义调用第三方失败异常〉
 *
 * @author hanxinghua
 * @create 2019/7/10
 * @since 1.0.0
 */
public class ThirdAPIFailException extends BusinessException {

    private static final long serialVersionUID = -832464574020190710L;

    public ThirdAPIFailException() {
        super();
    }

    public ThirdAPIFailException(Object data) {
        super.data = data;
    }

    public ThirdAPIFailException(ResultCode resultCode) {
        super(resultCode);
    }

    public ThirdAPIFailException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public ThirdAPIFailException(String msg) {
        super(msg);
    }

    public ThirdAPIFailException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }
}
