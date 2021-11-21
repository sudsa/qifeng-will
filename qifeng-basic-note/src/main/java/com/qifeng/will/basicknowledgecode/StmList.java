package com.hanxiaozhang.basicknowledgecode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StmList {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ClassStm{

        private String name;

        private String c1;

        private String c2;

        private String c3;

        private String c4;

    }
    public static void main(String[] args) {

        List<ClassStm> list = new ArrayList<ClassStm>(Arrays.asList(new ClassStm("jj","","","","")
                ,new ClassStm("aa","1","","","")
                ,new ClassStm("zz",null,"3","","")));
        list = list.stream().sorted(Comparator.comparing(ClassStm::getC1)
                .thenComparing(ClassStm::getC2)
        .thenComparing(ClassStm::getC3).thenComparing(ClassStm::getC4)
                .thenComparing(ClassStm::getName)).collect(Collectors.toList());
        System.out.println(list);
    }
}
