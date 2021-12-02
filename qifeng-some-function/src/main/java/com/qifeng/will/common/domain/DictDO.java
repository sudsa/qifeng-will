package com.qifeng.will.common.domain;

import com.hanxiaozhang.common.validation.CreateGroupValidation;
import com.hanxiaozhang.common.validation.UpdateGroupValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 字典表
 *
 * @author howill
 * @email howill@sina.com
 * @date 2020-04-30 09:43:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DictDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据字典ID
     */
    @NotNull(message="id不能为空!",groups ={UpdateGroupValidation.class})
    private Long id;

    /**
     * 标签名
     */
    @NotBlank(message="标签名不能为空!",groups ={CreateGroupValidation.class,UpdateGroupValidation.class})
    private String name;

    /**
     * 数据值
     */
    @NotBlank(message="数据值不能为空!",groups ={CreateGroupValidation.class,UpdateGroupValidation.class})
    private String value;

    /**
     * 类型
     */
    @NotBlank(message="类型不能为空!",groups ={CreateGroupValidation.class,UpdateGroupValidation.class})
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序（升序）
     */
    private BigDecimal sort;

    /**
     * 父级编号
     */
    private Long parentId;

    /**
     * 创建者
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 删除标记
     */
    private String delFlag;

}
