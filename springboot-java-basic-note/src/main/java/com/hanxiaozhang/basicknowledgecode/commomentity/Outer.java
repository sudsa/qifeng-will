package com.hanxiaozhang.basicknowledgecode.commomentity;

import java.util.ArrayList;
import java.util.List;

public class Outer {

    private String name;

    public List<String> getList(String item) {

        return new ArrayList<String>() {
            {
                add(item);
            }
        };
    }

    private void test() {

        class Inner {
            int  m= 3;
            private void print() {
                System.out.println(m);
                int i = m+1;
            }
        }
    }


}
