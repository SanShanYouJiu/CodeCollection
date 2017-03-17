package DesignMode.PrincipleDesign.ISP;

/**
 * Created by han on 2017/1/16.
 */
public class PrettyGirl implements IGoodBodyAndGreatTemperament {

    private String name;

    public PrettyGirl(String _name){
        this.name=_name;
    }

    @Override
    public void goodLooking() {
        System.out.println(this.name+"---脸蛋很漂亮");
    }

    @Override
    public void niceFigure() {
        System.out.println(this.name+"---身材特别棒");
    }

    @Override
    public void greatTemperamentGirl() {
        System.out.println(this.name+"----气质非常好");
    }


}
