package DesignMode.PrincipleDesign.ISP;

/**
 * Created by han on 2017/1/16.
 */
public class Searcher extends AbstractSearcher {


    public Searcher(IGreatTemperamentGirl pettyGirl) {
        super(pettyGirl);
    }

    public Searcher(IGoodBodyGirl pettyGirl) {
        super(pettyGirl);
    }

    public Searcher(IGoodBodyAndGreatTemperament pretty){
        super(pretty);
    }



    @Override
    public void show() {
        if (super.iGoodBodyGirl !=null) {
            System.out.println("美女的信息如下");
            super.iGoodBodyGirl.goodLooking();
            super.iGoodBodyGirl.niceFigure();
        } else if (super.iGreatTemperamentGirl != null) {
            super.iGreatTemperamentGirl.greatTemperamentGirl();
        } else  if (super.goodBodyAndGreatTemperament !=null){
            super.goodBodyAndGreatTemperament.greatTemperamentGirl();
            super.goodBodyAndGreatTemperament.goodLooking();
            super.goodBodyAndGreatTemperament.niceFigure();
         }else {
            System.out.println("不是美女");
        }
    }
}
