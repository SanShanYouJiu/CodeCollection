package DesignMode.DesignModel.Composite;

/**
 * Created by han on 2017/3/4.
 */
public class Leaf  extends Corp  {
    private String name = "";
    private String position = "";
    private int salary = 0;

    public Leaf(String name, String position, int salary) {
        super(name, position, salary);
    }


}
