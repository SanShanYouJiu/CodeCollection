package DesignMode.DesignModel.Memo.PrivateMemo;

/**
 * 备忘录管理者角色
 * Created by han on 2017/3/7.
 */
public class Caretaker {
    //备忘录角色
    private IMemento memento;

    public IMemento getMemento() {
        return memento;
    }

    public void setMemento(IMemento memento) {
        this.memento = memento;
    }
}
