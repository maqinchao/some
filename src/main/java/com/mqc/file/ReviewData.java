package com.mqc.file;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ReviewData {
    private String flag = "0";
    private String quesID;
    private List<SubQues> subQuesList;

    private Integer taskStudentNum;
}
