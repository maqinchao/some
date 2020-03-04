package com.mqc.file;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Administrator
 */
@Getter
@Setter
public class Student {

    /**
     * 评阅ID
     */
    private String evalID;
    /**
     *试题ID
     */
    private String resourceID;
    /**
     *小题序号，翻译题永为1
     */
    private int answerNum;
    /**
     *句子序号
     */
    private String clauseID;
    /**
     *类别序号
     */
    private String answerSequence;



    /**
     *规则序号，表示该学生使用哪条标准规则进行评分的
     */
    private String ruleIndex;

    private Boolean enable=false;
    /**
     *学号
     */
    private String studentID;
    /**
     *学生答案
     */
    private String studentAnswer;
    /**
     *学生分值
     */
    private String studentScore;

    private String comment;
    private Integer isTypical;
    /**
     * 批改网返回作文评阅标识
     */
    private String key;

    private String studentPredictScore;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return answerNum == student.answerNum &&
                Objects.equals(evalID, student.evalID) &&
                Objects.equals(resourceID, student.resourceID) &&
                Objects.equals(clauseID, student.clauseID) &&
                Objects.equals(answerSequence, student.answerSequence) &&
                Objects.equals(ruleIndex, student.ruleIndex) &&
                Objects.equals(enable, student.enable) &&
                Objects.equals(studentID, student.studentID) &&
                Objects.equals(studentAnswer, student.studentAnswer) &&
                Objects.equals(studentScore, student.studentScore) &&
                Objects.equals(comment, student.comment) &&
                Objects.equals(isTypical, student.isTypical);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evalID, resourceID, answerNum, clauseID, answerSequence, ruleIndex, enable, studentID, studentAnswer, studentScore, comment, isTypical);
    }
}
