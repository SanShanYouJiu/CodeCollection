package DesignMode.DesignModel.Decorate.Decorate2;

/**
 * Created by han on 2017/2/28.
 */
public class ConcreteComponent extends Component {
    //具体组件
    @Override
    public void operate() {
        System.out.println("do Something");
    }
}
