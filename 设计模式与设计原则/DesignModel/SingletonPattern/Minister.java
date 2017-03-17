package DesignMode.DesignModel.SingletonPattern;

/**
 * Created by han on 2017/1/18.
 */
public class Minister {
    public static void main(String[] args) {
        for (int day=0;day<3;day++) {
            Emperor emperor = Emperor.getInstance();
            emperor.say();
        }
    }
}
