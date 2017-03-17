package DesignMode.DesignModel.Decorate.Decorate2;

/**
 * Created by han on 2017/2/28.
 */
public  abstract class Decorator extends Component {
     private Component component=null;

    //通过构造函数传递给修饰者
    public Decorator(Component _component) {
        this.component=_component;
    }

    //委托给修饰者执行
    @Override
    public void operate() {
        this.component.operate();
    }

}
