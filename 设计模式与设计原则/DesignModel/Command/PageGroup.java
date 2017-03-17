package DesignMode.DesignModel.Command;

/**
 * Created by han on 2017/2/27.
 */
public class PageGroup extends Group {
    @Override
    public void find() {
        System.out.println("找到美工组....");
    }

    @Override
    public void add() {
        System.out.println("客户要求增加一个页面...");
    }

    @Override
    public void delete() {
        System.out.println("客户要求删除一个页面。。。");
    }

    @Override
    public void change() {
        System.out.println("客户要求修改一个页面。。。");
    }

    @Override
    public void plan() {
        System.out.println("客户要求页面变更计划。。。  ");
    }
}
