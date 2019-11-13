package com.mqc.word;

import com.github.houbb.word.checker.core.impl.EnWordChecker;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Administrator
 * @create 2019/11/12 11:36
 */
public class WordErrorTest {
    public static void main(String[] args) {
//        String split1="'| |.|．|。|！|？|｡|＂|＃|＄|％|＆|＇|（|）|＊|＋|，|－|／|：|；|＜|＝|＞|＠|［|＼|］|＾|＿|｀|｛|｜|｝|～|｟|｠|｢|｣|､|、|〃|》|「|」|『|』|【|】|〔|〕|〖|〗|〚|〛|〜|〝|〞|〟|–|—|‘|'|‛|“|”|„|‟|…|‧|﹏|.|!|\\|\"|#|$|%|&|'|(|)|*|+|,|-|.|/|:|;|<|=|>|?|@|[|\\|]|^|_|`|{|||}|~";
////        String split="．。！？｡＂＃＄％＆＇（）＊＋，－／：；＜＝＞＠［＼］＾＿｀｛｜｝～｟｠｢｣､、〃》「」『』【】〔〕〖〗〚〛〜〝〞〟–—‘'‛“”„‟…‧﹏.!\\\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
////        System.out.printf(String.join("|", Arrays.asList(split.split(""))));
//        String test="he's";
//        String [] arr=test.split(split1,-1);
//        boolean isCorrect= EnWordChecker.getInstance().isCorrect("s");
//        String result = EnWordChecker.getInstance().correct("s");
//        System.out.println(result);
        String  str="Newsgd.com is th2e 22 c,1";
        String s = "\\d+.\\d+|\\w+";
        Pattern pattern=Pattern.compile(s);
        Matcher ma=pattern.matcher(str);

        while(ma.find()){
            String answer=ma.group();
            if(answer.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$")){
                System.out.println(answer);
            }
        }
    }

//    public static boolean test(char[] arr){
//        boolean containsNumber=false;
//        boolean containsLetter=false;
//        for(char temp:arr){
//            if(!containsLetter){
//                if(temp<'z'&&temp>'a'||temp<'Z'&&temp>'A'){
//                    containsLetter=true;
//                }
//            }
//            if(){
//
//            }
//
//        }
//    }
}
