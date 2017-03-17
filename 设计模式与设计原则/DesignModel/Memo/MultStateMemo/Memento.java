package DesignMode.DesignModel.Memo.MultStateMemo;

import java.util.HashMap;

/**备忘录角色
 * Created by han on 2017/3/7.
 */
public class Memento {

    private HashMap<String, Object> stateMap;

    //构造函数传递参数
    public Memento(HashMap<String, Object> stateMap) {
        this.stateMap = stateMap;
    }

    public HashMap<String, Object> getStateMap() {
        return stateMap;
    }

    public void setStateMap(HashMap<String, Object> stateMap) {
        this.stateMap = stateMap;
    }

}
