package DesignMode.DesignModel.Command.Command2;

/**
 * Created by han on 2017/2/27.
 */
public class Client {
    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        Command command = new ConcreteCommand1();
        invoker.setCommand(command);
        invoker.action();
    }
}
