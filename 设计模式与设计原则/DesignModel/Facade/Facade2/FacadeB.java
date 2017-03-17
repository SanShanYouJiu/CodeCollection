package DesignMode.DesignModel.Facade.Facade2;

/**
 * Created by han on 2017/3/7.
 */
public class FacadeB {
    //引用原有的门面
    private Facade facade = new Facade();

    //对外提供唯一的访问子系统的方法  在面向对象的编程中 尽量保持相同的代码只写一遍
    // 避免以后到处修改相似代码的悲剧
    public void methodB(){
        this.facade.methodB();
    }
}
