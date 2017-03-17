package DesignMode.DesignModel.Memo;

/**
 * Created by han on 2017/3/7.
 */
public class Boy {
    private String state = "";

    public void changeState() {
        this.state = "心情可能不是很好";
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //保留一个备份
    public Memento createMemento() {
        return new Memento(this.state);
    }

    //恢复一个备份
    public void restoreMemento(Memento _menento) {
        this.setState(_menento.getState());
    }


}
