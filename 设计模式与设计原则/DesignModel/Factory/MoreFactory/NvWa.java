package DesignMode.DesignModel.Factory.MoreFactory;

/**
 * Created by han on 2017/1/19.
 */
public class NvWa {

    public static void main(String[] args) {
        System.out.println("造出第一批是白色人种");
        Human withehuman = (new BlaceHumanFactory()).crewateHuman( );
        withehuman.getColor();
        withehuman.talk();
        System.out.println("造出的第二批是黑色人种");
        Human blackhuman = (new WhiteHumanFactory()).crewateHuman( );
        blackhuman.getColor();
        blackhuman.talk();
        System.out.println("造出的第三批是黄色人种");
        Human yellowhuman = (new YellowHumanFactory()).crewateHuman( );
        yellowhuman.getColor();
        yellowhuman.talk();
    }

}
