package DesignMode.DesignModel.Factory;

/**
 * Created by han on 2017/1/19.
 */
public class NvWa {

    public static void main(String[] args) {
        AbstractHumanFactory YinyangLu = new HumanFactory();
        System.out.println("造出第一批是白色人种");
        Human withehuman = YinyangLu.crewateHuman(WhiteHuman.class);
        withehuman.getColor();
        withehuman.talk();
        System.out.println("造出的第二批是黑色人种");
        Human blackhuman = YinyangLu.crewateHuman(BlackHuman.class);
        blackhuman.getColor();
        blackhuman.talk();
        System.out.println("造出的第三批是黄色人种");
        Human yellowhuman = YinyangLu.crewateHuman(YellowHuman.class);
        yellowhuman.getColor();
        yellowhuman.talk();
    }

}
