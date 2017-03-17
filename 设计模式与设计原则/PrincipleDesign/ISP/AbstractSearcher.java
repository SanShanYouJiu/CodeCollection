package DesignMode.PrincipleDesign.ISP;

/**
 * Created by han on 2017/1/16.
 */
public abstract class AbstractSearcher {

    protected IGreatTemperamentGirl  iGreatTemperamentGirl;

    protected IGoodBodyGirl iGoodBodyGirl;

    protected  IGoodBodyAndGreatTemperament goodBodyAndGreatTemperament;

    public AbstractSearcher(IGreatTemperamentGirl pettyGirl) {
        this.iGreatTemperamentGirl = pettyGirl;
    }

    public AbstractSearcher(IGoodBodyGirl iGoodBodyGirl) {
        this.iGoodBodyGirl = iGoodBodyGirl;
    }

    public AbstractSearcher(IGoodBodyAndGreatTemperament iGoodBodyAndGreatTemperament) {
        this.goodBodyAndGreatTemperament = iGoodBodyAndGreatTemperament;
    }   

    public abstract void show();

}
