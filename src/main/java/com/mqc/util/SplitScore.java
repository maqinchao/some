package com.mqc.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author Administrator
 * @create 2019/11/19 11:26
 */
public class SplitScore {
    public static void main(String[] args) {
        Double [] scoreArr=getSplitScore2(10.0,201);
        System.out.printf("");
    }
    private static Double[] getSplitScore(Double answerPointScore,int size) {
        BigDecimal unitScore=null;
        BigDecimal unitCount=null;
        Double proportion=BigDecimal.valueOf(answerPointScore).divide(BigDecimal.valueOf(size),4, RoundingMode.HALF_UP).doubleValue();
        if(proportion>=0.25){
            unitScore=BigDecimal.valueOf(0.25);
            unitCount=BigDecimal.valueOf(4);
        }else if(proportion>=0.05){
            unitScore=BigDecimal.valueOf(0.05);
            unitCount=BigDecimal.valueOf(20);
        }else{
            //录入平台推送回分数时 翻译题直接根据分句前的试题总分计算总得分 各分句得分
            unitScore=new BigDecimal(answerPointScore);
            unitCount=BigDecimal.valueOf(1);
        }
        BigDecimal calculateScore=new BigDecimal(answerPointScore).multiply(unitCount);
        BigDecimal[] remainderResult=calculateScore.divideAndRemainder(new BigDecimal(size));

        BigDecimal avgScore=unitScore.multiply(remainderResult[0]);

        Double[] scoreArr=new Double[size];
        for(int i=0;i<size;i++){
            if(i<remainderResult[1].intValue()){
                scoreArr[i]=avgScore.add(unitScore).doubleValue();
            }else{
                scoreArr[i]=avgScore.doubleValue();
            }
        }
        return scoreArr;
    }

    public static Double[] getSplitScore2(Double answerPointScore,int size){
        Double[] pointScores=new Double[size];
        Double unitScore;
        Double proportion=BigDecimal.valueOf(answerPointScore).divide(BigDecimal.valueOf(size),4, RoundingMode.HALF_UP).doubleValue();
        if(proportion>=0.25){
            unitScore=0.25;
        }else if(proportion>=0.05){
            unitScore=0.05;
        }else{
            unitScore=0.01;
        }
        //增加初始值
        for(int i=0;i<size;i++){
            pointScores[i]=0.0;
        }
        int i=0;
        while(answerPointScore>unitScore){
            if(pointScores[i%size]==null){
                pointScores[i%size]=0.0;
            }
            pointScores[i%size]=pointScores[i%size]+unitScore;
            answerPointScore=answerPointScore-unitScore;
            i++;
        }
        if(answerPointScore>0){
            pointScores[i%size]=pointScores[i%size]+answerPointScore;
        }
        String[] scores = new String[size];
        for(int j=0;j<size;j++){
            scores[j]=String.format("%.2f", pointScores[j]);
        }
        return pointScores;
    }
}
