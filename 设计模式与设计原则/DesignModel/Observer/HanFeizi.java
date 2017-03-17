package DesignMode.DesignModel.Observer;

import java.util.Observable;

/**
 * Created by han on 2017/3/6.
 */
public class HanFeizi extends Observable implements  IHanFeiZi {

    @Override
    public void haveBreakfast() {
        System.out.println("韩非子 ：开始吃饭了");
        super.setChanged();
        this.notifyObservers("韩非子吃饭了");
    }

    @Override
    public void havefun() {
        System.out.println("韩非子开始娱乐了");
        super.setChanged();
        this.notifyObservers("韩非子开始娱乐了");
    }



}
