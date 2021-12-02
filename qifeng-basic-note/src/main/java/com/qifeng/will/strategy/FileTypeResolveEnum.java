package com.qifeng.will.strategy;

public enum FileTypeResolveEnum {

    A("a","a"),
    B("b","b"),
    C("c","c"),
    D("d","d");

    String code;
    String name;

    FileTypeResolveEnum() {
    }

    FileTypeResolveEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
