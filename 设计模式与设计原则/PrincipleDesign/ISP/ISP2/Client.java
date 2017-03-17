package DesignMode.PrincipleDesign.ISP.ISP2;

/**
 * Created by han on 2017/1/16.
 */
public class Client  {

    public static void main(String[] args) {
        IGoodBodyGirl pretty = new Pretty("艳艳");
        IGreatTemperamentGirl pretty2 = new Pretty("艳艳2");
        AbstractSearcher searcher = new Searcher(pretty);
        AbstractSearcher searcher2 = new Searcher(pretty2);
        searcher.show();
        searcher2.show();
    }
}
