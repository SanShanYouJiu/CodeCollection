package DesignMode.DesignModel.TemplateModel;

/**
 * Created by han on 2017/1/20.
 */
public abstract class HummerModel {
    public abstract  void start();
    public abstract  void  stop();
    public abstract  void aleram();
    public abstract  void engineBoom();

    public  final void run(){
        this.start();
        this.engineBoom();
        this.aleram();
        this.stop();
    }
}
