package DesignMode.DesignModel.Observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by han on 2017/3/6.
 */
public class LISI implements  Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("观察到韩非子的获得 开始向老板汇报了");
        this.reportToQinShiHuang(arg.toString());
    }


    private void reportToQinShiHuang(String reportContext) {
        System.out.println("李斯报告  秦始皇 韩非子有活动了---->"+reportContext);
    }
}
