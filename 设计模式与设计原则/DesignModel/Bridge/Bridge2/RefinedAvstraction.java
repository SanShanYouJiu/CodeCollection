package DesignMode.DesignModel.Bridge.Bridge2;

/**
 * Created by han on 2017/3/14.
 */
public class RefinedAvstraction extends Abstraction {
     //覆写构造函数
    public RefinedAvstraction(Implementor imp) {
        super(imp);
    }

    @Override
    public void request() {
        /**
         * 业务处理
         */
        super.request();
        super.getImp().doAnything();
    }
}
