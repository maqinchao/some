package com.mqc.leetcode;


import java.util.Arrays;

/**
 * @Author Administrator
 * @create 2020/1/18 11:32
 */
public class Trap {
    //kx=max(min[Y(x+1)-Y(x),Y(x-1)-Y(x)],0)
    //kx=max(min[T(x->)-Y(x),Y(<-x)-Y(x)],0)
    //Y<-xMax=max(Y(x-1),Y(x))
    //Yx->Max=
    public static int trap(int[] height) {
        if(height.length==0){
            return 0;
        }
        int sum=0;
        int ymax1=height[0];
        for(int x=1;x<height.length-1;x++){
            int kx1=ymax1-height[x];
            int kx2=findMaxY2(height,x)-height[x];
            int kx=Math.max(Math.min(kx1,kx2),0);
            height[x]=height[x]+kx;
            ymax1=Math.max(ymax1,height[x]);
            sum+=kx;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(trap(new int[]{4,2,3}));
    }

    public static int findMaxY2(int [] height,int x){
        int temp=height[x+1];
        for(int i=x+1;i<height.length;i++){
            temp=Math.max(height[i],temp);
        }
        return temp;
    }
}
