package DesignMode.NewDesign.Employees;

/**
 * Created by han on 2017/3/30.
 */
public class Kitchen implements Cleanable {
    @Override
    public void cleaned() {
        System.out.println("厨房被清洁干净");
    }
}
