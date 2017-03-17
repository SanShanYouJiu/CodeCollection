package DesignMode.DesignModel.Strategy;

/**
 * Created by han on 2017/3/1.
 */
public class BackDoor implements IStrategy {
    @Override
    public void opeate() {
        System.out.println("找乔国三帮忙 让吴老太给孙权施加压力");
    }
}
