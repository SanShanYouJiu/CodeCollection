package DesignMode.DesignModel.Decorate.Decorate2;

/**
 * Created by han on 2017/2/28.
 */
public class Client {

    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        component = new ConcreteDecorator1(component);
        component = new ConcreteDecorator2(component);
        component.operate();
    }
}
