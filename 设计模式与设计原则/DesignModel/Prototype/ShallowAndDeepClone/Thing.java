package DesignMode.DesignModel.Prototype.ShallowAndDeepClone;

import java.util.ArrayList;

/**
 * Created by han on 2017/2/24.
 */
public class Thing implements Cloneable {
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected Thing clone() throws CloneNotSupportedException {

        Thing thing = null;
        try {
            thing = (Thing) super.clone();
            thing.arrayList= (ArrayList<String>) arrayList.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return thing;
    }

    //设置HashMap的值
    public void setValue(String value) {
        this.arrayList.add(value);
    }

    //取得ArrayList的值
    public ArrayList<String> getValue() {
        return this.arrayList;
    }
}
