package DesignMode.NewDesign.Employees;

/**
 * Created by han on 2017/3/30.
 */
public class Garden implements Cleanable {
    @Override
    public void cleaned() {
        System.out.println("花园被清洁干净");
    }
}
