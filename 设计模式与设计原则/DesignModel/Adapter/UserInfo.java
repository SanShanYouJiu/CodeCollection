package DesignMode.DesignModel.Adapter;

/**
 * Created by han on 2017/3/2.
 */
public class UserInfo implements IUserInfo {
    @Override
    public String getUserName() {
        System.out.println("姓名叫做。。。");
        return null;
    }

    @Override
    public String getHomeAddress() {
        System.out.println("员工的家庭地址。。。");
        return null;
    }

    @Override
    public String getMobileNumber() {
        System.out.println("这个人的手机号码是。。。");
        return null;
    }

    @Override
    public String getOfficeNumber() {
        System.out.println("办公室电话。。。");
        return null;
    }

    @Override
    public String getJobPosition() {
        System.out.println("这个人的职务是BOSS...");
        return null;
    }

    @Override
    public String getHomeTelNumber() {
        System.out.println("员工的家庭电话。。。");
        return null;
    }
}
