package DesignMode.DesignModel.Flyweight.FlyWeight;

/**
 * Created by han on 2017/3/17.
 */
public abstract class Flyweight {
    private String  innerVar;

    private final String ExternalVar;

    protected Flyweight(String externalVar) {
        ExternalVar = externalVar;
    }


    public String getInnerVar() {
        return innerVar;
    }

    public void setInnerVar(String innerVar) {
        this.innerVar = innerVar;
    }
}
