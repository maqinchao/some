package com.mqc.design.proxy;

public class Suitors implements Pursue{
    @Override
    public void sendGift(String name) {
        System.out.println(name+" 送礼物");
    }

    @Override
    public void sendFlower(String name) {
        System.out.println(name+" 送花");
    }
}
