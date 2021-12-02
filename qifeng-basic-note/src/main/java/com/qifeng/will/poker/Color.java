package com.qifeng.will.poker;

import java.util.Arrays;

public enum Color {
    DIAMOND, //方块
    CLUB,   //梅花
    HEART,  //红桃
    SPADE;  //黑桃
    private String[] colors = {"方块", "梅花", "红桃", "黑桃"};

    @Override
    public String toString() {
        return colors[this.ordinal()];
    }
}
