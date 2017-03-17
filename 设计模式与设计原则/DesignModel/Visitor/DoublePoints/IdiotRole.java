package DesignMode.DesignModel.Visitor.DoublePoints;

/**
 * Created by han on 2017/3/10.
 */
public class IdiotRole implements Role {
    //一个弱智角色
    @Override
    public void accept(AbsActor actor) {
     actor.act(this);
    }

}
