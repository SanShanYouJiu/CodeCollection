package DesignMode.DesignModel.Command;

/**
 * Created by han on 2017/2/27.
 */
public abstract class Group {
    public abstract void find();

    public abstract  void add();

    public abstract void delete();

    public abstract  void change();

    public  abstract  void plan();

    public void rollBack(){
        //根据日志进行回滚
    }
}
