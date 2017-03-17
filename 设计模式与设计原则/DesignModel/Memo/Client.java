package DesignMode.DesignModel.Memo;

/**
 * Created by han on 2017/3/7.
 */
public class Client {
    public static void main(String[] args) {
        Boy boy = new Boy();
        Caretaker caretaker = new Caretaker();
        boy.setState("心情很棒！");
        System.out.println("----男孩现在的状态");
        System.out.println(boy.getState());
        //需要记录当前的状态
        caretaker.setMemento(boy.createMemento());
        boy.changeState();
        System.out.println("\n==========男孩追女孩子后的状态===========");
        System.out.println(boy.getState());
        //追女孩失败 恢复原状
        boy.restoreMemento(caretaker.getMemento());
        System.out.println("\n======男孩恢复后的状态");
        System.out.println(boy.getState());
    }
}
