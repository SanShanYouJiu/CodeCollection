package DesignMode.DesignModel.Command.Command2;

/**
 * Created by han on 2017/2/27.
 */
public abstract class Command {
    //定义一个子类的全局共享变量
     protected  final  Receiver receiver;
    //实现类必须定义一个接受者
    protected Command(Receiver receiver) {
        this.receiver = receiver;
    }

    public  abstract void execute();
}
