package DesignMode.DesignModel.Adapter.ExtendAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by han on 2017/3/3.
 */
public class OuterHomeInfo implements IOuterUserHomeInfo {
    @Override
    public Map getUserOfficeInfo() {
        HashMap homeInfo = new HashMap();
        homeInfo.put("homeTelNumbner", "员工的家庭电话是、、");
        homeInfo.put("homeAddress", "员工的家庭地址是。。");
        return homeInfo;
    }


}
