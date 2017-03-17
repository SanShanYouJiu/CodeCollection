package DesignMode.DesignModel.Composite;

import java.util.ArrayList;

/**
 * Created by han on 2017/3/4.
 */
public class Branch  extends Corp  {
    private ArrayList suborinateList = new ArrayList();
    private String name = "";
    private String position = "";
    private int salary=0;

    public Branch(String name, String position, int salary) {
        super(name, position, salary);
    }


    public void addSubordinate(Corp corp) {
        corp.setParent(this);
        this.suborinateList.add(corp);
    }

    public ArrayList getSubordinateInfo() {
        return this.suborinateList;
    }
}
