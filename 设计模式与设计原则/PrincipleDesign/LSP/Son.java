package DesignMode.PrincipleDesign.LSP;


import java.util.Map;

/**
 * Created by han on 2017/1/14.
 */
public class Son extends Father {


    public Object dosomething(Map hashMap) {
        System.out.println("子类 dosomething..");
        return new Object();
    }


}
