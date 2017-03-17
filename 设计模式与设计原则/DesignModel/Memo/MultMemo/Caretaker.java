package DesignMode.DesignModel.Memo.MultMemo;


import java.util.HashMap;

/**
 * Created by han on 2017/3/9.
 */
public class Caretaker {
    //容纳备忘录的容器
    private HashMap<String, Memento> memMap = new HashMap<String, Memento>();

    public Memento getMemento(String idex) {
        return memMap.get(idex);
    }

    public void setMemento(String idx,Memento memento) {
        this.memMap.put(idx, memento);
    }

}
