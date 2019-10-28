package com.mqc.some;

public class test2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(Thread.interrupted()){
                        System.out.println();
                    }
                }
            }
        });
        thread.setName("t1");

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        System.out.println(thread.isInterrupted());
        System.out.println(thread.interrupted());

//        System.out.println(thread.isInterrupted());
//        System.out.println(thread.isInterrupted());
    }
}
