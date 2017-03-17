package DesignMode.DesignModel.AbstractFactory;

/**
 * Created by han on 2017/1/19.
 */
public class NvWa {
    public static void main(String[] args) {
        HumanFactory maleHumanFactory = new MaleFactory();
        HumanFactory femaleHumanFactory = new FemaleFactory();

        Human maleYellowHuman = maleHumanFactory.createYellowHuman();
        maleYellowHuman.getColor();
        maleYellowHuman.talk();
        maleYellowHuman.getSex();
        Human FemaleYellowHumane = femaleHumanFactory.createYellowHuman();
        FemaleYellowHumane.getColor();
        FemaleYellowHumane.talk();
        FemaleYellowHumane.getSex();

    }
}
