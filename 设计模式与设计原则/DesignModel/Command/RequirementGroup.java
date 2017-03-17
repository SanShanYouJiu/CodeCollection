package DesignMode.DesignModel.Command;

/**
 * Created by han on 2017/2/27.
 */
public class RequirementGroup extends Group {
    @Override
    public void find() {
        System.out.println("找到需求组。。。。。。");
    }

    @Override
    public void add() {
        System.out.println("客户要求增加一项要求。。。。");
    }

    @Override
    public void delete() {
        System.out.println("客户要求删除一项要求。。。。");
    }

    @Override
    public void change() {
        System.out.println("客户要求删除一项需求。。。。。");
    }

    @Override
    public void plan() {
        System.out.println("客户要求需求变更计划。。。");
    }
}
