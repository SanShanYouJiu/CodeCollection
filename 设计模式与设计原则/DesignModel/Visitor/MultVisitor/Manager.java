package DesignMode.DesignModel.Visitor.MultVisitor;

/**
 * Created by han on 2017/3/9.
 */
public class Manager extends Employee {
    private String performance;

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    @Override
    public void accept( IVisitor visitor) {
         visitor.visit(this);
    }

}
