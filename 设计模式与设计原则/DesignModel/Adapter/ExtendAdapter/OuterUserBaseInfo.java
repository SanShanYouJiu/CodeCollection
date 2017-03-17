package DesignMode.DesignModel.Adapter.ExtendAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by han on 2017/3/3.
 */
public class OuterUserBaseInfo implements IOuterUserBaseInfo {
    @Override
    public Map getUserBaseInfo() {
        HashMap baseInfoMap = new HashMap();
        baseInfoMap.put("username", "这个员工叫混世魔王。");
        baseInfoMap.put("mobileNumber", "这个员工的电话号码.");
        return baseInfoMap;
    }
}
