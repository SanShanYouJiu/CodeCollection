package DesignMode.DesignModel.State;

/**
 * Created by han on 2017/3/11.
 */
public class RunningState extends LiftState {
    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public void run() {
        System.out.println("电梯上下在运行。。");
    }

    @Override
    public void stop() {
        super.context.setLiftState(Context.stopingState);
        super.context.stop();
    }
}
