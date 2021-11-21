package com.hanxiaozhang.designpattern.strategy.constant;

/**
 * 〈一句话功能简述〉<br>
 * 〈Process流程枚举类〉
 *
 * @author hanxinghua
 * @create 2020/5/14
 * @since 1.0.0
 */
public enum ProcessTypeEnum {

    A("aProcess","流程A"),
    B("bProcess","流程B");

    private String key;
    private String name;

    ProcessTypeEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ProcessTypeEnum{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
