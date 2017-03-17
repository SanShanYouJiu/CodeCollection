package DesignMode.DesignModel.TemplateModel.TemplateModel3;


/**
 * Created by han on 2017/1/20.
 */
public class HummeH2Model extends HummerModel {
    @Override
    public void start() {
        System.out.println("悍马H2发动");
    }

    @Override
    public void stop() {
        System.out.println("悍马H2停车");
    }

    @Override
    public void aleram() {
        System.out.println("悍马H2鸣笛");
    }

    @Override
    public void engineBoom() {
        System.out.println("悍马H2引擎声音是这样的。。。");
    }

    @Override
    protected boolean isAlarm() {
        return false;
    }
}
