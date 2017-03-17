package DesignMode.DesignModel.Visitor.MultVisitor;


/**
 * Created by han on 2017/3/9.
 */
public class TotalVisitor implements ITotalVisitor {
    //经理的工资系数
    private final static int MANAGER_COEFICTENT = 5;
    //员工的工资系数
    private final static int COMMONEMPLOYEE_COEFFICIENT = 2;
    //员工工资
    private int commonTotalSalry = 0;
    //经理工资
    private int managerTotalSalary = 0;


    public void visit(CommonEmployee commonEmployee) {
        this.managerTotalSalary = this.managerTotalSalary + commonEmployee.getSalary() * MANAGER_COEFICTENT;
    }

    public void visit(Manager manager) {
        this.commonTotalSalry = this.commonTotalSalry + manager.getSalary() * COMMONEMPLOYEE_COEFFICIENT;
    }

    //计算所有员工的工资总和
    @Override
    public void totalSalary() {
        System.out.println("本公司的月工资总额是：" + this.commonTotalSalry + this.managerTotalSalary);
    }


}
