package DesignMode.DesignModel.Visitor.MultVisitor;

/**
 * Created by han on 2017/3/10.
 */
public class ShowVisitor implements IShowVisitor {
    private String info = "";

    @Override
    public void report() {
        System.out.println(this.info);
    }

    public void visit(CommonEmployee commonEmployee) {
        this.info = this.info + this.getBasicinfo(commonEmployee) + "工作："
                + commonEmployee.getJob() + "\t\n";
    }

    public void visit(Manager manager) {
        this.info = this.info+this.getBasicinfo(manager) + "业绩:" + manager.getPerformance() + "\t\n";
    }


    private String getBasicinfo(DesignMode.DesignModel.Visitor.MultVisitor.Employee employee) {
        String info = "姓名" + employee.getName() + "\t";
        info = info + "性别" + (employee.getSex() == DesignMode.DesignModel.Visitor.MultVisitor.Employee.FEMALE ? "女" : "男") + "\t";
        info = info + "薪水" + employee.getSalary() + "\t";
        return info;
    }

}
