package DesignMode.DesignModel.Prototype.ShallowAndDeepClone;

/**
 * Created by han on 2017/2/24.
 */
public class Client {

    public static void main(String[] args) throws CloneNotSupportedException {
        Thing thing = new Thing();
        thing.setValue("张三");
        Thing cloneThing =  thing.clone();
        cloneThing.setValue("李四");
        System.out.println(thing.getValue());

    }
}
