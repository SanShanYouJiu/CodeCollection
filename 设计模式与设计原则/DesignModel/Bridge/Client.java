package DesignMode.DesignModel.Bridge;

/**
 * Created by han on 2017/3/14.
 */
public class Client {
    public static void main(String[] args) {
        House house = new House();
        System.out.println("----------房地产公司是这样运行的--------");
        HouseCorp houseCorp = new HouseCorp(house);
        houseCorp.makeMoney();
        System.out.println("\n");
        System.out.println("-------------服装公司是这样运行的----------");
        ShanZhaICorp shanZhaICorp = new ShanZhaICorp(new IPod());
        shanZhaICorp.makeMoney();

    }
}
