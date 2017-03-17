package DesignMode.DesignModel.TemplateModel;

/**
 * Created by han on 2017/1/20.
 */
public class HummeH1Model extends  HummerModel {
    @Override
    public void start() {
        System.out.println("悍马H1发动");
    }

    @Override
    public void stop() {
        System.out.println("悍马H1停车");
    }

    @Override
    public void aleram() {
        System.out.println("悍马H1鸣笛");
    }

    @Override
    public void engineBoom() {
        System.out.println("悍马H1引擎声音是这样的。。。");
    }


}
