package DesignMode.DesignModel.Adapter.Adapter2;

/**
 * Created by han on 2017/3/2.
 */
public class Client {
    public static void main(String[] args) {
        Target target = new ConcreteTarget();
        target.request();
        Target target2 = new Adapter();
        target2.request();
    }
}
