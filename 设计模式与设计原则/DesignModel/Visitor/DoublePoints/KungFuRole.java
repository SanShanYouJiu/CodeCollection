package DesignMode.DesignModel.Visitor.DoublePoints;

/**
 * Created by han on 2017/3/10.
 */
public class KungFuRole implements Role {
    //武功天下第一的角色
    @Override
    public void accept(AbsActor actor) {
        actor.act(this);
    }

}
