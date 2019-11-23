package com.mqc.leetcode;

/**
 * @Author Administrator
 * @create 2019/11/21 19:53
 */
public class NumDecodings {
    public static int numDecodings(String s) {
        String [] arr=s.split("");
        Integer[] numArr=new Integer[arr.length];
        int index=1;
        numArr[0]= Integer.valueOf(arr[0]);
        for(int n=1;n<arr.length;n++){
            int preNum= Integer.parseInt(arr[n-1]);
            int localNum= Integer.parseInt(arr[n]);
            int temp= Integer.parseInt(arr[n-1]+arr[n]);
            if(localNum==0&&preNum==0){
                return 0;
            }
            if(temp>26&&localNum==0){
                return 0;
            }
            if(localNum==0){
                numArr[index-1]= temp;
            }else{
                numArr[index]= localNum;
                index++;
            }
        }
        //sum[n]=sum[n-1]+int(arr[n-1]+arr[n])<26?
        int sum[]=new int[index];
        sum[0]=1;
        if(arr[0].equals("0")){
            return 0;
        }
        for(int n=1;n<index;n++){
            int localNum= numArr[n];
            int preNum=numArr[n-1];
            boolean isAppend=check(localNum);
            boolean isPreAppend=check(preNum);

            if(isAppend||isPreAppend){
                sum[n]=sum[n-1];
            }else{
                int temp= Integer.parseInt(""+preNum+localNum);
                if (temp<=26){
                    sum[n]=sum[n-1]+(n>1?sum[n-2]:1);
                }else{
                    sum[n]=sum[n-1];
                }
            }

        }
        return sum[sum.length-1];
    }

    public static boolean check(int temp){
        if(String.valueOf(temp).length()>1){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(numDecodings("12121"));
    }
}
/*一条包含字母 A-Z 的消息通过以下方式进行了编码：

'A' -> 1
'B' -> 2
...
'Z' -> 26
给定一个只包含数字的非空字符串，请计算解码方法的总数。

示例 1:

输入: "12"
输出: 2
解释: 它可以解码为 "AB"（1 2）或者 "L"（12）。
示例 2:

输入: "226"
输出: 3
解释: 它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/decode-ways
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/
