

package com.qifeng.will.web.view.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/**
 * @ClassName: ResponseResult
 * @Description: 统一请求返回结果model
 * ResponseResultList<UcsMember> resp = gson.fromJson(result, new TypeToken<ResponseResultList<UcsMember>>() {}.getType());
 * @author Qing.Luo
 * @date 2015年5月28日 上午10:07:59
 * @version
 * @param <T>
 */
public class ResponseResultList<T> extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5297238356739532766L;

	/**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param
     */
    public ResponseResultList() {
        super();
    }

    private Integer totalCount;

    private List<T> data;

    /* 不提供直接设置errorCode的接口，只能通过setErrorInfo方法设置错误信息 */
    // private String errorCode;

    public static <T> ResponseResultList<T> newInstance() {
        return new ResponseResultList<T>();
    }

    public List<T> getData() {
        if (data == null) {
            return new ArrayList<T>();
        }
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    // public String getErrorCode() {
    // return errorCode;
    // }

    /**
     * <p>
     * Title: toString
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * @return the totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     *            the totalCount to set
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
