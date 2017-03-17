package DesignMode.DesignModel.Composite;

/**
 * Created by han on 2017/3/4.
 */
public abstract class Corp {
    private String name = "";
    private String position = "";
    private int salary = 0;

    private Corp parent;

    public Corp(String name, String position, int salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public Corp getParent() {
        return parent;
    }

    public void setParent(Corp parent) {
        this.parent = parent;
    }

    /**
     * 获得员工信息
     * @return
     */
    public String getInfo() {
        String info = "";
        info = "姓名" + this.name;
        info = info + "\t职位"+this.position;
        info = info + "\t薪水" + this.salary;
        return info;
    }

}
