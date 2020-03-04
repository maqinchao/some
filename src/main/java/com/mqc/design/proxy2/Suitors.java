package com.mqc.design.proxy2;

/**
 * @Author Administrator
 * @create 2020/2/29 9:16
 */
public class Suitors implements Pursue {
    @Override
    public void sendGift(String name) {
        System.out.println(name+" 送礼物");
    }

    @Override
    public void sendFlower(String name) {
        System.out.println(name+" 送花");
    }
}
