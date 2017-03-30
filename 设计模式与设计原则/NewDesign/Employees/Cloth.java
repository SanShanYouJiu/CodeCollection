package DesignMode.NewDesign.Employees;

/**
 * Created by han on 2017/3/30.
 */
public class Cloth implements Cleanable {
    @Override
    public void cleaned() {
        System.out.println("衣服被清洁干净");
    }

}
