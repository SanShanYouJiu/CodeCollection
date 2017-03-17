package DesignMode.DesignModel.Adapter.ExtendAdapter;


import DesignMode.DesignModel.Adapter.IUserInfo;

/**
 * Created by han on 2017/3/3.
 */
public class Client {
    public static void main(String[] args) {
        IOuterUserBaseInfo baseInfo = new OuterUserBaseInfo();
        IOuterUserHomeInfo homeInfo = new OuterHomeInfo();
        IOuterUserOfficeInfo officeInfo = new OuterUserOfficeInfo();
        IUserInfo youngGirl = new OuterUserInfo(baseInfo, homeInfo, officeInfo);
        for (int i = 0; i < 101; i++) {
            youngGirl.getMobileNumber();
        }
    }
}
