package DesignMode.DesignModel.Visitor.MultVisitor;



/**
 * Created by han on 2017/3/9.
 */
public abstract  class Employee {
    public final  static  int MALE=0;
    public final  static  int FEMALE=1;
    private String name;
    private int salary;
    private int sex;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void accept( IVisitor visitor);
}
