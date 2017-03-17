package DesignMode.DesignModel.Memo.Memo2;

/**
 * 备忘录管理者角色
 * Created by han on 2017/3/7.
 */
public class Caretaker {
    //备忘录角色
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
