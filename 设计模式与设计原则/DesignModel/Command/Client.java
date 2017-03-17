package DesignMode.DesignModel.Command;

/**
 * Created by han on 2017/2/27.
 */
public class Client {

    public static void main(String[] args) {
        Invoker xiaoSan = new Invoker();
        //首先客户要求需求组 过来谈需求 并修改
        System.out.println("-----------客户要求要求一项需求---------------");
        Command command = new DeletePageCommand();
        xiaoSan.setCommand(command);
        xiaoSan.action();
    }
}
