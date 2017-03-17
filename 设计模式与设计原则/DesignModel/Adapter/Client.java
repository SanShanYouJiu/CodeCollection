package DesignMode.DesignModel.Adapter;

/**
 * Created by han on 2017/3/2.
 */
public class Client {
    public static void main(String[] args) {
        IUserInfo youngGirl = new OuterUserInfo();
        for (int i = 0; i <101 ; i++) {
            youngGirl.getMobileNumber();
        }
    }
}
