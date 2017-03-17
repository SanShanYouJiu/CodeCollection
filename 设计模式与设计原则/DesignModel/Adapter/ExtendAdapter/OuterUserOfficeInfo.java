package DesignMode.DesignModel.Adapter.ExtendAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by han on 2017/3/3.
 */
public class OuterUserOfficeInfo implements IOuterUserOfficeInfo {
    @Override
    public Map getUserOfficeInfo() {
        HashMap officeInfo = new HashMap();
        officeInfo.put("jobPosition", "这个人的职务是BOSS..");
        officeInfo.put("officeTelNumer", "员工的办公电话是、、");
        return officeInfo;
    }
}
