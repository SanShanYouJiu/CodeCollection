package DesignMode.DesignModel.Visitor;

/**
 * Created by han on 2017/3/9.
 */
public class CommonEmployee  extends Employee{
    private String  job;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    //允许访问
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
