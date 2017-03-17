package DesignMode.PrincipleDesign.ISP.ISP2;

/**
 * Created by han on 2017/1/16.
 */
public class Pretty implements IGoodBodyGirl, IGreatTemperamentGirl {
     private String name;

    public Pretty(String name) {
        this.name = name;
    }

    @Override
    public void goodLooking() {
        System.out.println(this.name+"----看起来漂亮");
    }

    @Override
    public void niceFigure() {
        System.out.println(this.name+"----身材好");
    }

    @Override
    public void greatTemperamentGirl() {
        System.out.println(this.name+"----气质非常棒");
    }
}
