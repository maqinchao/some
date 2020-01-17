package com.mqc.file;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Administrator
 * @create 2019/12/2 11:38
 */
public class HandleFile {
    public static void main(String[] args) {
        test1119();
    }

    private static void test1119() {
        List<String> list=toArrayByFileReader1("E:\\mqc\\project\\some\\src\\main\\resources\\info.2020-01-15.17.log");
        List<String> list2=toArrayByFileReader1("E:\\mqc\\project\\some\\src\\main\\resources\\info.2020-01-15.18.log");
        list.addAll(list2);
        List<String> filterList_S=list.stream().filter(e->e.contains("发出请求-")).map(e->e.split("发出请求-|,",-1)[1]).collect(Collectors.toList());
        List<String> filterList_R=list.stream().filter(e->e.contains("收到结果-")).map(e->e.split("收到结果-|,",-1)[1]).collect(Collectors.toList());
        List<String> filterList_R2=list.stream().filter(e->e.contains("收到响应-")).map(e->e.split("收到响应-|,",-1)[1]).collect(Collectors.toList());
        filterList_S.removeAll(filterList_R);
        filterList_S.removeAll(filterList_R2);
        System.out.println();
    }

    private static void test1503() {
        List<String> list=toArrayByFileReader1("C:\\Users\\Administrator\\Desktop\\xml\\CFCE33202DHg1000B1D_1_g.xml");
        List<String> answerFilter=list.stream().filter(e->e.contains("<answer>"))
                .map(e->e.replaceAll("<answer>|</answer>","").trim())
                .collect(Collectors.toList());

        List<String> scoreFilter=list.stream().filter(e->e.contains("<score>"))
                .map(e->e.replaceAll("<score>|</score>","").trim())
                .collect(Collectors.toList());
        List<String> indexFilter=list.stream().filter(e->e.contains("<serial_number>"))
                .map(e->e.replaceAll("<serial_number>|</serial_number>","").trim())
                .collect(Collectors.toList());

        List<ReviewObject> reviewObjects=new ArrayList<>();

        for(int i=0;i<indexFilter.size();i++){
            ReviewObject reviewObject=new ReviewObject();
            reviewObject.setIndex(indexFilter.get(i));
            reviewObject.setAnswer(answerFilter.get(i));
            reviewObject.setScore(scoreFilter.get(i));
            reviewObjects.add(reviewObject);
        }
        ExcelUtil.getWriter(true);
        ExcelWriter excelWriter= ExcelUtil.getWriter("C:\\Users\\Administrator\\Desktop\\xml\\CFCE33202DHg1000B1D_1_g.xlsx");
        excelWriter.write(reviewObjects);
        excelWriter.close();
    }

    public static void findFileList(File dir, List<String> fileNames) {
        if (!dir.exists() || !dir.isDirectory()) {// 判断是否存在目录
            return;
        }
        String[] files = dir.list();// 读取目录下的所有目录文件信息
        for (int i = 0; i < files.length; i++) {// 循环，添加文件名或回调自身
            File file = new File(dir, files[i]);
            if (file.isFile()) {// 如果文件
                fileNames.add(dir + "\\" + file.getName());// 添加文件全路径名
            } else {// 如果是目录
                findFileList(file, fileNames);// 回调自身继续查询
            }
        }
    }

    private static void handleLog3() {
        List<String> list=toArrayByFileReader1("E:\\mqc\\project\\some\\src\\main\\resources\\CFCE33202DHg1000B1D-无分");
        List<String> filterList=list.stream().filter(e->e.contains("answerContent"))
                .map(e->e.replaceAll("\t|answerContent|\"|:",""))
                .map(s->s.substring(1,s.length() - 1)).collect(Collectors.toList());

        List<String> list2=toArrayByFileReader1("E:\\mqc\\project\\some\\src\\main\\resources\\CFCE33202DHg1000B1D-有分");
        List<String> filterList2=list2.stream().filter(e->e.contains("answerContent"))
                .map(e->e.replaceAll("\t|answerContent|\"|:",""))
                .map(s->s.substring(1,s.length() - 1)).collect(Collectors.toList());

        List<String> list3=filterList2.stream().filter(e->filterList.contains(e)).collect(Collectors.toList());
        System.out.printf("");
    }

    private static void handleLog2() {
        List<String> list=toArrayByFileReader1("E:\\mqc\\project\\some\\src\\main\\resources\\CFAC04023DHg1000B0i_1_g.xml");
        List<List<String>> filterList=list.stream().filter(e->e.contains("<answer>"))
                .map(e->e.split("<answer>")[1].split("</answer>")[0])
                .collect(Collectors.groupingBy(e->e)).values().stream().filter(e->e.size()>1).collect(Collectors.toList());
        System.out.printf("");
    }

    private static void handleLog() {
        List<String> list=toArrayByFileReader1("C:\\Users\\Administrator\\Desktop\\113\\log\\info.2019-12-01.log");
        List<String> filterList_R=list.stream().filter(e->e.contains("收到")).collect(Collectors.toList());
        List<String> filterList_S=list.stream().filter(e->e.contains("发出")).collect(Collectors.toList());
        List<String>  knowledgeCount_R=filterList_R.stream().filter(e->e.contains("State")).collect(Collectors.toList());
        List<String>  knowledgeCount_S=filterList_S.stream().filter(e->e.contains("10109")&&!e.contains("WS_Search_GetServerAddressConf")).collect(Collectors.toList());
        List<String>  reviewCount_R=filterList_R.stream().filter(e->e.contains("subQuesSeq")&&e.contains("收到")).collect(Collectors.toList());
        List<String>  reviewCount_S=filterList_S.stream().filter(e->e.contains("16101")&&e.contains("发出")).collect(Collectors.toList());

        List<String> noneResult=new ArrayList<>();
        for(String str:reviewCount_S){
            String[] temp=str.split("param:");
            if(!reviewCount_R.stream().filter(e->e.contains(temp[1])).findAny().isPresent()){
                noneResult.add(str);
            }
        }
        System.out.printf("");
    }

    public static ArrayList<String> toArrayByFileReader1(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(name);
            InputStreamReader isr = new InputStreamReader(fis,"GB2312");
            BufferedReader bf = new BufferedReader(isr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

}
