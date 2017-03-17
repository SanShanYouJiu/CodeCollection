package DesignMode.DesignModel.Flyweight.Flyweight2;

/**具体享元角色1
 * Created by han on 2017/3/13.
 */
public class ConcreteFlyWeight1 extends Flyweight {

    //接受外部状态
    protected ConcreteFlyWeight1(String extrinsic) {
        super(extrinsic);
    }


    //定义业务操作
    @Override
    public void operate() {
       //业务逻辑
    }

}
