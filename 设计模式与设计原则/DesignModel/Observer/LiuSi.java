package DesignMode.DesignModel.Observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by han on 2017/3/6.
 */
public class LiuSi implements Observer {

    private void happy(String context){
        System.out.println("刘斯 因为"+context+"我开心啊");
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("刘斯  观察到韩非子活动了");
        this.happy(arg.toString());
        System.out.println("刘斯 开心死了");
    }
}
