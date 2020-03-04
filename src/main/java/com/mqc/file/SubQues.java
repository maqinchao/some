package com.mqc.file;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class SubQues {

    private String subQuesIndex;

    private String quesType;

    private List<AttributeAndTypicalResult> answerPointList;

    public SubQues() {
    }

    public SubQues(String subQuesIndex, String quesType, List<AttributeAndTypicalResult> answerPointList) {
        this.subQuesIndex = subQuesIndex;
        this.quesType = quesType;
        this.answerPointList = answerPointList;
    }

    public List<AttributeAndTypicalResult> getTargetAnswerPointList(Integer answerPointIndex ){
        for(AttributeAndTypicalResult e:this.answerPointList){
            if(Objects.equals(e.getAnswerPointIndex(),answerPointIndex)) {
                return Arrays.asList(e);
            }
        }
        return null;
    }
}
