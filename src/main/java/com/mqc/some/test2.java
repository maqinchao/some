package com.mqc.some;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

public class test2 {
    public static void main(String[] args){
        Bean bean=new Bean();
        bean.setName1("1");
        bean.setName2(2);
        String beanStr= JSON.toJSONString(bean);
        System.out.printf(beanStr);
    }
}

@Setter
@Getter
class Bean{
    private String name1;
    private Integer name2;
}