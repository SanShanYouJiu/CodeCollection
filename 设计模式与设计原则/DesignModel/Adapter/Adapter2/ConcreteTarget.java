package DesignMode.DesignModel.Adapter.Adapter2;

/**目标角色的实现类
 * Created by han on 2017/3/2.
 */
public class ConcreteTarget implements Target {
    @Override
    public void request() {
        System.out.println("if you need any helo,pls call me!");
    }

}
