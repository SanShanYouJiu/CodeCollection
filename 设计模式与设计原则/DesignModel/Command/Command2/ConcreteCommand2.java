package DesignMode.DesignModel.Command.Command2;

public class ConcreteCommand2 extends Command {

    //声明自己的默认接受者
    public ConcreteCommand2(){
        super(new ConcreteReceiver2());
    }

    //设置新的接受者
    public ConcreteCommand2(Receiver _receiver) {
        super(_receiver);
    }


    //必须实现一个命令
    @Override
    public void execute() {
        //业务处理
        this.receiver.doSomething();
    }
}