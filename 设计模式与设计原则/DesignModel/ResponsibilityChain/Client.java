package DesignMode.DesignModel.ResponsibilityChain;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by han on 2017/2/28.
 */
public class Client {

    public static void main(String[] args) {
        Random rand = new Random();
        ArrayList<IWomen> arrayList = new ArrayList<IWomen>();
        for (int i = 0; i <5 ; i++) {
            arrayList.add(new Woman(rand.nextInt(4), "我要出去逛街"));
        }
        Handler father = new Father();
        Handler husband = new Husband();
        Handler son = new Son();
        father.setNextHandler(husband);
        husband.setNextHandler(son);
        for (IWomen women : arrayList) {
            father.HandleMessage(women);
        }
    }
}
