package DesignMode.DesignModel.State;

/**上下文类
 * Created by han on 2017/3/11.
 */
public class Context {
    //定义所有电梯状态
    public final static OpenningState openningState = new OpenningState();
    public final static CloseingState closeingState = new CloseingState();
    public final static RunningState runningState = new RunningState();
    public final static StopingState stopingState = new StopingState();
   //定义当前一个电梯状态
    private LiftState liftState;

    public LiftState getLiftState() {
        return liftState;
    }

    public void setLiftState(LiftState liftState) {
        this.liftState = liftState;
        this.liftState.setContext(this);
    }

    public void open(){
        this.liftState.open();
    }

    public void close(){
        this.liftState.close();
    }

    public void run(){
        this.liftState.run();
    }

    public void stop(){
        this.liftState.stop();
    }
}

