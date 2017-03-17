package DesignMode.DesignModel.Observer.Observer2;

/**
 * Created by han on 2017/3/6.
 */
public class ConcreteObserver implements Observer {
    @Override
    public void update() {
        System.out.println("接收到信息 并进行处理！");
    }
}
