package DesignMode.PrincipleDesign.DIP;

/**
 * Created by han on 2017/1/15.
 */
public class Client {

    public static void main(String[] args) {
        iDriver zhangSan = new Driver();
        Benz benz = new Benz();
        zhangSan.Driver(benz);
    }
}
