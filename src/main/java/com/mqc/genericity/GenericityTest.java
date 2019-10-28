package com.mqc.genericity;

import java.util.ArrayList;
import java.util.List;

/**
 * java 上界 ? extends 与下界 ? super
 */
public class GenericityTest {
    public static void main(String[] args) {
        //? extends Parents 确定上界Parents 说明参数化的类型为Parents或其派生类
        List<? extends Parents> upperBoundsList = new ArrayList<>();

        //初始化时 可以指向一切 Parents及Parents派生类的容器
        List<? extends Parents> upperBoundsList1 = new ArrayList<Son>();

        //而以下语句错误 因为Parents与Son的派生关系无法扩展至 List<Parents> 与 List<Son>
        //List<Parents> upperBoundsList3= new ArrayList<Son>(); error

        //放入的时候 除了null外无法放入元素 编译器无法确定放入的应该是Parents的哪一个具体的派生类
        //list1.add(new Parents()); error
        //list1.add(new Son()); error
        upperBoundsList.add(null);

        //获取时可以用Parents或者GrandParents接
        Parents p = upperBoundsList.get(0);
        GrandParents gp = upperBoundsList.get(0);
        Son s = (Son) upperBoundsList.get(0);

        //? super Parents 确定下界Parents 说明参数化类型为Parents及其父类
        List<? super Parents> lowerBoundsList = new ArrayList<>();

        //初始化时 可以指向GrandParents Parents
        List<? super Parents> lowerBoundsList1 = new ArrayList<GrandParents>();

        //添加元素时 可以添加Parents及Son  因为无论添加Parents的哪一级子类 都可以向上转型为Parents及其父类一直到Object
        //类似
        List<Parents> normalList=new ArrayList<>();
        normalList.add(new Son());//这一种情况

        lowerBoundsList.add(new Parents());
        lowerBoundsList.add(new Son());

        //无法添加Patents的父类
        //lowerBoundsList.add(new GrandParents()); error
        //原因和? extends Parents相同 无法确定到具体的哪一个父类上

        //而接收时 无法确定到底接收的是哪一个父类 所以统一用Object最上级来接收
        Object o=lowerBoundsList.get(0);
        Parents p1= (Parents) lowerBoundsList.get(0);
    }
}

class GrandParents {

}

class Parents extends GrandParents {

}

class Son extends Parents {

}

