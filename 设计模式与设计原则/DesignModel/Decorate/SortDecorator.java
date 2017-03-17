package DesignMode.DesignModel.Decorate;

/**
 * Created by han on 2017/2/28.
 */
public class SortDecorator extends Decorator {

    public SortDecorator(SchoolReport sr) {
        super(sr);
    }

    private  void reportSort(){
        System.out.println("我是排名第38名。。。。");
    }


    @Override
    public void report() {
        super.report();
        this.reportSort();
    }
}
