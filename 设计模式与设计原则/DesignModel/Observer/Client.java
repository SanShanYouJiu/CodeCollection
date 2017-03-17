package DesignMode.DesignModel.Observer;
import java.util.Observer;

/**
 * Created by han on 2017/3/6.
 */
public class Client {
    public static void main(String[] args) {
        Observer liSi = new LISI();
        Observer wangSi = new WangSi();
        Observer Liusi = new LiuSi();
        HanFeizi hanFeizi = new HanFeizi();
        hanFeizi.addObserver(liSi);
        hanFeizi.addObserver(wangSi);
        hanFeizi.addObserver(Liusi);
        hanFeizi.havefun();
    }
}
