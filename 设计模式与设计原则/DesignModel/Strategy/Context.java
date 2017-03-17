package DesignMode.DesignModel.Strategy;

/**
 * Created by han on 2017/3/1.
 */
public class Context {
    private IStrategy strategy;

    public Context(IStrategy iStrategy) {
        this.strategy = iStrategy;
    }

    public  void  operate(){
        this.strategy.opeate();
    }
}
