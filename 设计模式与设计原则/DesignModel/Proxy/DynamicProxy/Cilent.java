package DesignMode.DesignModel.Proxy.DynamicProxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by han on 2017/2/23.
 */
public class Cilent {

    public static void main(String[] args) {
        IGamePlayer player = new GamePlayer("张三");
        InvocationHandler handler = new GamePlayIH(player);
        System.out.println("开始时间是:2009-8-25 10:45");
        ClassLoader cl = player.getClass().getClassLoader();
        IGamePlayer proxy= (IGamePlayer) Proxy.newProxyInstance(cl, new Class[]{IGamePlayer.class},handler);
        proxy.login("zhangSan", "password");
        proxy.killBoss();
        proxy.upgrade();
        System.out.println("结束时间是:2009-8-26 03:40");
    }
}
