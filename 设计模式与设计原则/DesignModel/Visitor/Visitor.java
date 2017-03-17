package DesignMode.DesignModel.Visitor;

/**
 * Created by han on 2017/3/9.
 */
public class Visitor  implements IVisitor {
    //经理的工资系数
    private final static int MANAGER_COEFICTENT = 5;
    //员工的工资系数
    private final  static  int COMMONEMPLOYEE_COEFFICIENT=2;
    //员工工资
    private int commonTotalSalry = 0;
    //经理工资
    private int managerTotalSalary = 0;

    public void visit(CommonEmployee commonEmployee) {
        System.out.println(this.getCommonsInfo(commonEmployee));
        this.calCommonSalary(commonEmployee.getSalary());
    }

    public void visit(Manager manager) {
        System.out.println(this.getManagerInfo(manager));
        this.calManagerSalary(manager.getSalary());
    }

    //计算所有员工的工资总和
    @Override
    public int getTotalSalary() {
        return  this.commonTotalSalry+this.managerTotalSalary;
    }

   //计算普通员工的工资总和
    private void calCommonSalary(int salary){
         this.managerTotalSalary=this.managerTotalSalary+salary*MANAGER_COEFICTENT;
    }

    //计算部门经理的工资总和
    private void calManagerSalary(int salary) {
        this.commonTotalSalry = this.commonTotalSalry +salary*COMMONEMPLOYEE_COEFFICIENT;
    }

    private String getBasicinfo(Employee employee) {
        String info="姓名"+employee.getName()+"\t";
        info = info + "性别" + (employee.getSex()==Employee.FEMALE?"女":"男") + "\t";
        info=info+"薪水"+employee.getSalary()+"\t";
        return info;
    }

    private String getManagerInfo(Manager manager) {
        String basicInfo = this.getBasicinfo(manager);
        String otherInfo = "业绩" + manager.getPerformance() + "\t";
        return basicInfo + otherInfo;
    }

    private String getCommonsInfo(CommonEmployee commonEmployee) {
        String basicInfo = this.getBasicinfo(commonEmployee);
        String otherInfo = "工作" + commonEmployee.getJob();
        return basicInfo + otherInfo;
    }

}
