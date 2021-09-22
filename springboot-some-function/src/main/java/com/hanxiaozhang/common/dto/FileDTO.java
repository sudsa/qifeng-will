package com.hanxiaozhang.common.dto;

import com.hanxiaozhang.common.domain.FileDO;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/4/30
 * @since 1.0.0
 */
@Data
public class FileDTO extends FileDO {

    /**
     * 文件类型名称
     */
    private String typeName;

    /**
     * 文件标签
     */
    private String fileTagName;


    /**
     * uuid
     */
    private String uuid;

}
