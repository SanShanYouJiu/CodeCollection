package DesignMode.DesignModel.Memo.CloneMemo;

/**
 * 发起人自主的备份和恢复
 * Created by han on 2017/3/9.
 */
public class Originator implements Cloneable {
    private Originator back;
    //内部状态
    private String state = "";

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //创建一个备忘录
    public void createMemento()  {
        this.back= this.clone();
    }

    //恢复一个备忘录
    public void restoreMemento() {
        //在进行恢复前应该断言 防止空指针
        this.setState(this.back.getState());
    }


    //克隆当前对象
    @Override
    protected Originator clone()  {
        try {
            return (Originator) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
