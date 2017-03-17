package DesignMode.DesignModel.Adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by han on 2017/3/2.
 */
public class OuterUser implements IOuterUser {

    @Override
    public Map getUserBaseInfo() {
        HashMap baseInfoMap = new HashMap();
        baseInfoMap.put("userName", "这个员工叫混世魔王。。。");
        baseInfoMap.put("mobileNumber", "这个员工电话是。。。");
        return baseInfoMap;
    }

    @Override
    public Map getUserOfficeInfo() {
        HashMap homeinfo = new HashMap();
        homeinfo.put("homeTelNumber", "员工的家庭电话是。。。");
        homeinfo.put("homeAddress", "员工的家庭地址。。。");
        return homeinfo;
    }


    @Override
    public Map getUserHomeInfo() {
        HashMap officeInfo = new HashMap();
        officeInfo.put("jobPosition", "这个人的职务是BOSS。。");
        officeInfo.put("officeTelNumber", "员工的办公电话。。。");
        return officeInfo;
    }


}
