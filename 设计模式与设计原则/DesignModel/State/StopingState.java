package DesignMode.DesignModel.State;

/**
 * Created by han on 2017/3/11.
 */
public class StopingState extends LiftState {
    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {
        System.out.println("电梯停止了。。。");
    }
}
