package DesignMode.DesignModel.Proxy.CompelProxy;

/**
 * Created by han on 2017/2/22.
 */
public class Client {
    public static void main(String[] args) {
        IGamePlayer player = new GamePlayer("张三");
        IGamePlayer proxy = player.getProxy();
        System.out.println("开始时间是:2009-8-25 10:45");
        proxy.login("zhangSan", "password");
        proxy.killBoss();
        proxy.upgrade();
        System.out.println("结束时间是：2009-8-26 03:40");
    }
}
