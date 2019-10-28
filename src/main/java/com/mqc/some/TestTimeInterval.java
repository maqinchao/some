package com.mqc.some;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;

public class TestTimeInterval {
    public static void main(String[] args) throws InterruptedException {
        TimeInterval timeInterval= DateUtil.timer();
        timeInterval.start();
        Thread.sleep(1000);
        System.out.println(timeInterval.intervalRestart());
        Thread.sleep(1000);
        System.out.println(timeInterval.intervalRestart());
    }
}
