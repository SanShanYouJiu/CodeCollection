package DesignMode.DesignModel.Visitor;

/**
 * Created by han on 2017/3/9.
 */
public interface IVisitor {
    void visit(CommonEmployee commonEmployee);

    void visit(Manager manager);

    int getTotalSalary();
}
