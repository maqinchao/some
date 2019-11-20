package stream;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Administrator
 * @create 2019/11/19 10:06
 */
@Data
public class WordBean {
    private List<Letter> letters=new ArrayList<>();
    private String word="word";
    public static WordBean buildWordBean(){
        WordBean wordBean=new WordBean();
        List<Letter> letters=new ArrayList<>();
        for(int i=0;i<10;i++){
            letters.add(Letter.buildLetter());
        }
        wordBean.setLetters(letters);
        return wordBean;
    }
    public static void main(String[] args) {
        List<WordBean> wordBeanList=new ArrayList<>();
        for(int i=0;i<10;i++){
            wordBeanList.add(WordBean.buildWordBean());
        }
//        List<Stream<Letter>> streamList=wordBeanList.stream().map(e->e.getLetters()).map(e->e.stream()).collect(Collectors.toList());
                                                        //List<List<Letter>>            //Stream<Letter>
        List<String> letterList=wordBeanList.stream().map(e->e.getLetters()).flatMap(e->e.stream()).map(e->e.getContent()).collect(Collectors.toList());


    }
}
