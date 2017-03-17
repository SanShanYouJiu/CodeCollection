package DesignMode.PrincipleDesign.ISP.ISP2;

/**
 * Created by han on 2017/1/16.
 */
public abstract class AbstractSearcher {

     protected IGoodBodyGirl goodBodyGirl;

     protected IGreatTemperamentGirl greatTemperamentGirl;

    public AbstractSearcher(IGoodBodyGirl goodBodyGirl) {
        this.goodBodyGirl = goodBodyGirl;
    }

    public AbstractSearcher(IGreatTemperamentGirl greatTemperamentGirl) {
        this.greatTemperamentGirl = greatTemperamentGirl;
    }

    protected  abstract void show();
}
