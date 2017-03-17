package DesignMode.DesignModel.TemplateModel.TemplateModel2;

/**
 * Created by han on 2017/1/20.
 */
public abstract class AbstractClass {
    //基本方法
    protected abstract  void dosomthing();
   //基本方法
    protected  abstract  void doAnything();

    //模板方法
    public void templateMethod(){
        this.doAnything();
        this.dosomthing();
    }
}
