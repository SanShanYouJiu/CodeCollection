package DesignMode.DesignModel.Factory.MoreFactory;

/**
 * Created by han on 2017/1/19.
 */
public class WhiteHumanFactory extends AbstractHumanFactory {
    @Override
    public Human crewateHuman() {
        return new WhiteHuman();
    }
}
