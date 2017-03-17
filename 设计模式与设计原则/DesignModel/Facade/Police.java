package DesignMode.DesignModel.Facade;

/**
 * Created by han on 2017/3/7.
 */
public class Police {
    public void checkLetter(ILetterProcess letterProcess) {
        System.out.println(letterProcess+"信件已经检测过了...");
    }
}
