package DesignMode.DesignModel.Prototype.Protorype2;

/**通用原型模式
 * Created by han on 2017/2/24.
 */
public class PrototypeClass implements Cloneable {



    @Override
    protected PrototypeClass clone() throws CloneNotSupportedException {
        PrototypeClass prototypeClass = null;
        try {
            prototypeClass = (PrototypeClass) super.clone();
        } catch (CloneNotSupportedException e) {
         //异常处理
        }
        return prototypeClass;
    }


    public static void main(String[] args) {

    }
}
