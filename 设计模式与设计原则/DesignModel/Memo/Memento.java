package DesignMode.DesignModel.Memo;

/**备忘录
 * Created by han on 2017/3/7.
 */
public class Memento {
    private String state = "";
    public Memento(String _state){
        this.state=_state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
