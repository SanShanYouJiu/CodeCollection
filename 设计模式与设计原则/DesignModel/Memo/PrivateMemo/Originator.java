package DesignMode.DesignModel.Memo.PrivateMemo;

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
    public IMemento creaeteMemento() {
        return new Memento(state);
    }


    //恢复一个备忘录
    public void  restorMemento(IMemento _memento){
         this.setState(((Memento)_memento).getState());
    }


    private class  Memento implements IMemento{
        //发起人内部状态
        private String state = "";
        //构造函数传递参数

        private Memento(String state) {
            this.state = state;
        }

        private String getState() {
            return state;
        }

        private void setState(String state) {
            this.state = state;
        }
    }
}
