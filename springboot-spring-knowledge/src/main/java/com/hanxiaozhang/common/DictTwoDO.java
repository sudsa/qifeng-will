package com.hanxiaozhang.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class DictTwoDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //编号
    private Long id;
    //标签名
    private String name;
    //数据值
    private String value;
    //类型
    private String type;
    //描述
    private String description;
    //排序（升序）
    private BigDecimal sort;
    //父级编号
    private Long parentId;
    //创建者
    private Integer createBy;
    //创建时间
    private Date createDate;
    //更新者
    private Long updateBy;
    //更新时间
    private Date updateDate;
    //备注信息
    private String remarks;
    //删除标记
    private String delFlag;
}
