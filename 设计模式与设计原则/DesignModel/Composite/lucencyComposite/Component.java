package DesignMode.DesignModel.Composite.lucencyComposite;


import java.util.ArrayList;

/**抽象构件
 * Created by han on 2017/3/4.
 */
public abstract class Component {
    private Component parent = null;


    //个体和整体都具有的共享
    public void doSomething() {
        //编写业务逻辑
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }

    public Component getParent() {
        return parent;
    }


    //增加一个叶子构件或树枝构件
    public abstract void add(Component component);

    //删除一个叶子构件或树枝构件
    public abstract void remove(Component component) ;

    //获得分支下的所有叶子构件和树枝构件
    public  abstract  ArrayList<Component> getChildren() ;

}
