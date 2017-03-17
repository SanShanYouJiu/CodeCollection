package DesignMode.DesignModel.Command;

/**
 * Created by han on 2017/2/27.
 */
public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action(){
        this.command.execute();
    }
}
