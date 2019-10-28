package com.mqc.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = new int[101];
        for (int i = 1; i < 101; i++){
            arr[i]=i;
        }
        EsaForkJoinTask task=new EsaForkJoinTask(arr);
        ForkJoinPool pool=new ForkJoinPool();
        pool.submit(task);
        System.out.println(task.get());
    }

}

class EsaForkJoinTask extends RecursiveTask<Integer>{

    private int[] arr;

    public EsaForkJoinTask() {
    }

    public EsaForkJoinTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        int sum=0;
        if(arr.length<2){
            sum+= Arrays.stream(arr).sum();
        }else {
            int mid=arr.length/2;
            int[] leftArr=new int[mid];
            int[] rightArr=new int[arr.length-mid];
            for(int i=0;i<arr.length;i++){
                if(i<mid){
                    leftArr[i]=arr[i];
                }else{
                    rightArr[i-mid]=arr[i];
                }
            }
            EsaForkJoinTask left=new EsaForkJoinTask(leftArr);
            EsaForkJoinTask right=new EsaForkJoinTask(rightArr);

            invokeAll(left,right);

            sum+=left.join();
            sum+=right.join();
        }
        return sum;
    }
}


