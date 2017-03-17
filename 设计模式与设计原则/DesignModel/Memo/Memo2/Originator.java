package DesignMode.DesignModel.Memo.Memo2;

/**发起人角色
 * Created by han on 2017/3/7.
 */
public class Originator {
    //内部状态
    private String state = "";

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //创建一个备忘录
    public Memento creaeteMemento() {
        return new Memento(state);
    }
    //恢复一个备忘录
    public void  restorMemento(Memento _memento){
         this.setState(_memento.getState());
    }

}
