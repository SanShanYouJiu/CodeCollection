package DesignMode.DesignModel.Command.Command2;

/**
 * Created by han on 2017/2/27.
 */
public class ConcreteCommand1 extends Command {
    //声明自己的默认接受者
    public ConcreteCommand1() {
        super(new ConcreteReceiver1());
    }

    //设置新的接受者
    public ConcreteCommand1(Receiver _receiver) {
        super(_receiver);
    }

    //必须实现一个命令
    @Override
    public void execute() {
        //业务处理
        this.receiver.doSomething();
    }
}
