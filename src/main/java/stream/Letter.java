package stream;

import lombok.Data;

/**
 * @Author Administrator
 * @create 2019/11/19 10:07
 */
@Data
public class Letter {
    private String content;
    public static Letter buildLetter(){
        Letter letter=new Letter();
        letter.setContent("1");
        return letter;
    }
}
