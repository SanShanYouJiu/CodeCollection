package DesignMode.DesignModel.AbstractFactory;

/**
 * Created by han on 2017/1/19.
 */
public class MaleYellowHuman extends AbstractYellowHuman {
    @Override
    public void getSex() {
        System.out.println("黄种男人");
    }
}
