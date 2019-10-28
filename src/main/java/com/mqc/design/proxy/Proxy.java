package com.mqc.design.proxy;

public class Proxy implements Pursue{
    private Suitors suitors;

    public Proxy() {
    }

    public Proxy(Suitors suitors) {
        this.suitors = suitors;
    }

    @Override
    public void sendGift(String name) {
        suitors.sendGift(name);
    }

    @Override
    public void sendFlower(String name) {
        suitors.sendFlower(name);
    }

    public static void main(String[] args) {
        Proxy proxy=new Proxy(new Suitors());
        proxy.sendGift("girl");
        proxy.sendFlower("girl");
    }
}
