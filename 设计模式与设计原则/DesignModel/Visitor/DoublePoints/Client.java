package DesignMode.DesignModel.Visitor.DoublePoints;

/**
 * Created by han on 2017/3/10.
 */
public class Client {
    public static void main(String[] args) {
        AbsActor actor = new OldActor();
        Role role = new KungFuRole();
        role.accept(actor);
    }
}
