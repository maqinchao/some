package com.mqc.file;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Administrator
 */
@Setter
@Getter
public class AttributeAndTypicalResult extends AnswerReviewPointResult {
    private List<Student> studentData;
    private Integer categoryNum;

    /**
     * 仅作文题型使用
     * 作文标题
     */
    private String title;
    /**
     * 仅作文题型使用
     * 作文号
     */
    private String rid;

}
