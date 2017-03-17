package DesignMode.DesignModel.Memo.Memo2;

/**
 * Created by han on 2017/3/7.
 */
public class Client {
    public static void main(String[] args) {
        //定义出发起人
        Originator originator = new Originator();
        //定义出备忘录管理员
        Caretaker caretaker = new Caretaker();
        //创建一个备忘录
        caretaker.setMemento(originator.creaeteMemento());
        //恢复一个备忘录
        originator.restorMemento(caretaker.getMemento());
    }
}
