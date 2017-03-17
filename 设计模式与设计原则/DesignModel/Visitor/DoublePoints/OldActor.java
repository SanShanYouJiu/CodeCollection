package DesignMode.DesignModel.Visitor.DoublePoints;

/**
 * Created by han on 2017/3/10.
 */
public class OldActor extends AbsActor {

    @Override
    public void act(KungFuRole role) {
        System.out.println("年龄大了  不演功夫角色");
    }

}
