package com.mqc.file;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Administrator
 */
@Setter
@Getter
public class AnswerReviewPointResult {
    private Integer answerPointIndex;
    private String answerPointContent;
    private String answerPointRefAnswer="";
    private String answerPointScore;
}