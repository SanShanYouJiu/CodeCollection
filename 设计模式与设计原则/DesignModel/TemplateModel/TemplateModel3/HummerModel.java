package DesignMode.DesignModel.TemplateModel.TemplateModel3;

/**
 * Created by han on 2017/1/20.
 */
public abstract class HummerModel {
    protected abstract  void start();
    protected abstract  void  stop();
    protected abstract  void aleram();
    protected abstract  void engineBoom();

    final public    void run(){
        this.start();
        this.engineBoom();
        if (this.isAlarm()) this.aleram();
        this.stop();
    }
    protected boolean isAlarm(){
        return  true;
    }
}
