package DesignMode.DesignModel.State.State2;

/**
 * Created by han on 2017/3/11.
 */
public class ConcreteState2  extends State{
    @Override
    public void handle1() {
        //设置当前状态state1
        super.context.setCurrentState(Context.STATE1);
        //过渡到state1状态 由Context实现
        super.context.handle1();
    }

    @Override
    public void handle2() {
       //本状态下必须处理的逻辑
    }
}
