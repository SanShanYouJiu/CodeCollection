package DesignMode.DesignModel.Factory;

/**
 * Created by han on 2017/1/19.
 */
public abstract class AbstractHumanFactory {
    public abstract  <T extends Human> T  crewateHuman(Class<T> c);
}
