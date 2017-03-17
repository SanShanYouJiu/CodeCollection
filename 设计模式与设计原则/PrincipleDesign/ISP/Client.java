package DesignMode.PrincipleDesign.ISP;

/**
 * Created by han on 2017/1/16.
 */
public class Client {

    public static void main(String[] args) {
        IGoodBodyGirl yanYan = new PrettyGirl("燕燕");
        AbstractSearcher searcher = new Searcher(yanYan);
        searcher.show();
    }
}
