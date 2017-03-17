package DesignMode.DesignModel.Visitor.DoublePoints;

/**
 * Created by han on 2017/3/10.
 */
public interface Role {

    //演员要扮演的角色
    public void accept(AbsActor actor);
}
