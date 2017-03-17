package DesignMode.DesignModel.Flyweight.Flyweight2;

/**具体享元角色2
 * Created by han on 2017/3/13.
 */
public class ConcreteFlyWeight2 extends Flyweight {

    //接受外部状态
    protected ConcreteFlyWeight2(String extrinsic) {
        super(extrinsic);
    }

    //根据外部状态进行逻辑处理
    @Override
    public void operate() {
         //业务逻辑
    }
}
