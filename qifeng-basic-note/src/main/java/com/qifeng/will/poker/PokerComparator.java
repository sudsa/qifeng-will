package com.qifeng.will.poker;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class PokerComparator implements Comparator<Poker> {
    @Override
    public int compare(Poker o1, Poker o2) {
        if(!Objects.equals(o1.getColor(),o2.getColor())){
            return Integer.compare(o2.getColor().ordinal(),o1.getColor().ordinal());
        }else{

            return Integer.compare(o2.getPoint(),o1.getPoint());
        }
    }

    public static void main(String[] args) {
        List<Poker> pokerList = new ArrayList<Poker>();

        Poker poker = new Poker(1, Color.DIAMOND);
        Poker poker2 = new Poker(1, Color.SPADE);
        Poker poker3 = new Poker(2, Color.HEART);
        Poker poker4 = new Poker(6, Color.HEART);
        Poker poker5 = new Poker(1, Color.CLUB);
        Poker poker6 = new Poker(1, Color.HEART);
        Poker poker7 = new Poker(7, Color.SPADE);
        Poker poker8 = new Poker(8, Color.SPADE);
        Poker poker9= new Poker(8, Color.CLUB);

        pokerList.add(poker);
        pokerList.add(poker2);
        pokerList.add(poker3);
        pokerList.add(poker4);
        pokerList.add(poker5);
        pokerList.add(poker6);
        pokerList.add(poker7);
        pokerList.add(poker8);
        pokerList.add(poker9);
        System.out.println("bf:"+pokerList);
        Collections.sort(pokerList, new PokerComparator(){});

        System.out.println("af:"+pokerList);
    }
}
