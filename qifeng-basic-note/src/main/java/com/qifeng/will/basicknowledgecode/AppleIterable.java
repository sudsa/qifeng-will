package com.hanxiaozhang.basicknowledgecode;

import lombok.Data;

import java.util.Iterator;

public class AppleIterable implements Iterable<Apple>{

    private Apple[] apple;

    @Override
    public Iterator<Apple> iterator() {
        return new AppIterator();
    }

    // 实现Iterator接口的私有内部类，外界无法直接访问
    private class AppIterator implements Iterator<Apple> {
        // 当前迭代元素的下标
        private int index = 0;

        // 判断是否还有下一个元素，如果迭代到最后一个元素就返回false
        public boolean hasNext() {
            return index != apple.length;
        }

        @Override
        public Apple next() {
            return apple[index++];
        }

        // 这里不支持，抛出不支持操作异常
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
@Data
class Apple{
    private String name;

    private int price;
}
