package DesignMode.DesignModel.Flyweight.Flyweight2;

/**抽象享元角色
 * Created by han on 2017/3/13.
 */
public abstract class Flyweight {
    //内部状态
    private String intrinsic;

    //外部状态
    protected final  String Extrinsic;

    //要求享元角色必须接受外部状态
    protected Flyweight(String extrinsic) {
        Extrinsic = extrinsic;
    }

    //定义业务操作
    public abstract void operate();


    //内部状态的getter/setter
    public String getIntrinsic() {
        return intrinsic;
    }

    public void setIntrinsic(String intrinsic) {
        this.intrinsic = intrinsic;
    }

}
