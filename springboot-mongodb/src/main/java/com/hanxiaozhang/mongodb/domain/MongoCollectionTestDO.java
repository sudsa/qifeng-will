package com.hanxiaozhang.mongodb.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈MongoDB集合测试实体类〉
 *
 * @author hanxinghua
 * @create 2020/6/14
 * @since 1.0.0
 */
@Data
// 标注在实体类上，类似于hibernate的entity注解，标明由mongo来维护该表，不加默认类名即表明
@Document(collection= "mongocollection_test")
public class MongoCollectionTestDO implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 信息
     */
    private String info;

    /**
     * 创建时间
     */
    @Field("create_time")
    @JsonFormat( pattern="yyyy-MM-dd")
    private Date createTime;

}
