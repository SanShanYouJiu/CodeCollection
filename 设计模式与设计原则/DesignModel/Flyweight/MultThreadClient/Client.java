package DesignMode.DesignModel.Flyweight.MultThreadClient;

/**
 * Created by han on 2017/3/13.
 */
public class Client {
    public static void main(String[] args) {
        SignInfoFactory.getSignInfo("科目1");
        SignInfoFactory.getSignInfo("科目2");
        SignInfoFactory.getSignInfo("科目3");
        SignInfoFactory.getSignInfo("科目4");
        SignInfo signInfo = SignInfoFactory.getSignInfo("科目2");
        while (true) {
            signInfo.setId("ZhangSan");
            signInfo.setLocation("ZhangSan");
            (new MultThread(signInfo)).start();
            signInfo.setId("Lisi");
            signInfo.setLocation("Lisi");
            (new MultThread(signInfo)).start();
        }
    }
}
