package DesignMode.DesignModel.Command.Command2;

/**
 * Created by han on 2017/2/27.
 */
public class Invoker {

    private Command command;

    //接受命令
    public void setCommand(Command command) {
        this.command = command;
    }

    //执行命令
    public void action() {
        this.command.execute();
    }

}
