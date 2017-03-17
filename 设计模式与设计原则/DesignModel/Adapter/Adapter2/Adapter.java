package DesignMode.DesignModel.Adapter.Adapter2;

/**适配器角色
 * Created by han on 2017/3/2.
 */
public class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        super.doSomething();
    }
}
