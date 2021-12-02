package com.qifeng.will.strategy;

/*
 *功能描述 策略模式
 * @author zouhw02
 * @date 2021/10/27
 * @param
 * @return
 */
public interface IFileStrategy {

    //属于哪种文件解析类型
    FileTypeResolveEnum gainFileType();

    //封装的公用算法（具体的解析方法）
    void resolve(Object objectparam);

}
