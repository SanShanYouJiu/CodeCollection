package DesignMode.DesignModel.Observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by han on 2017/3/6.
 */
public class WangSi implements Observer {

    private void cry(String  context){
        System.out.println("王斯：因为"+context+"---所以悲伤了");
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("王斯 观察到韩非子获得 自己也开始活动了...");
        this.cry(arg.toString());
        System.out.println("王斯 哭死了");
    }
}
