package DesignMode.DesignModel.SingletonPattern;

/**
 * Created by han on 2017/1/18.
 */
public class Emperor {

    private static final Emperor emperor = new Emperor();


    private Emperor(){

    }

    public static Emperor getInstance() {
        return emperor;
    }

    public static  void say(){
        System.out.println("我是皇帝某某某");
    }

}
