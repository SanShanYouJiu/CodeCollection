package DesignMode.PrincipleDesign.ISP.ISP2;

/**
 * Created by han on 2017/1/16.
 */
public class Searcher extends AbstractSearcher {

    public Searcher(IGoodBodyGirl goodBodyGirl) {
        super(goodBodyGirl);
     }

     public Searcher(IGreatTemperamentGirl greatTemperamentGirl){
         super(greatTemperamentGirl);
     }


    @Override
    protected void show() {
          if (super.goodBodyGirl!=null){
              super.goodBodyGirl.niceFigure();
              super.goodBodyGirl.goodLooking();
          }else if(super.greatTemperamentGirl!=null){
              super.greatTemperamentGirl.greatTemperamentGirl();
          }else{
              System.out.println("不是美女");
          }
    }
}
