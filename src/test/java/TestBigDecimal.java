import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;

/**
 * @Author Administrator
 * @create 2019/12/31 15:48
 */
public class TestBigDecimal {
    public static void main(String[] args) {
        System.out.println( NumberUtil.toStr(94.0f));;

    }

    private static void test2() {
        String str1="172.16.41.255:10100";
        String[] ipAndPortArr=str1.split(":",-1);

        String str2="http://172.16.41.111:10103/WS_TBookEditor/Aitest_Reviw_PushMarkResultByReviewClouds.ashx";
        String regex = "(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))";
        System.out.println(ipAndPortArr[0].matches(regex));
        Integer port = Integer.valueOf(ipAndPortArr[1]);
        System.out.println(port <65535&&port>0);

        String [] strArr=str2.split("/WS_TBookEditor/Aitest_Review_PushMarkResultByReviewClouds.ashx|http://|:",-1);
        if(strArr.length!=4||!strArr[0].equals("")||!strArr[3].equals("")){
            System.out.println("error");
        }
        System.out.println(strArr[1].matches(regex));
        Integer port2 = Integer.valueOf(ipAndPortArr[2]);
        System.out.println(port2 <65535&&port2>0);
        System.out.println(str2.matches(regex));
    }

    private static void test1() {
        BigDecimal decimal=new BigDecimal(12.665);
        System.out.println(decimal.setScale(2,BigDecimal.ROUND_HALF_DOWN));
        System.out.println(decimal.setScale(1,BigDecimal.ROUND_HALF_DOWN));
    }
}
