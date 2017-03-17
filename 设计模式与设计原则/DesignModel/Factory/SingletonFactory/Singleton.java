package DesignMode.DesignModel.Factory.SingletonFactory;

/**
 * Created by han on 2017/1/19.
 */
public class Singleton {
 private  static Singleton singleton=new Singleton();

    private Singleton(){

    }

    public static Singleton getIngleton(){
        return  singleton;
    }

}

