package DesignMode.NewDesign.Employees;

/**
 * Created by han on 2017/3/30.
 */
public class Client {
    public static void main(String[] args) {
        Cleaner cookie = new Cleaner();
        cookie.clean(new Kitchen());
        //园丁清洁花园
        Cleaner gradener = new Cleaner();
        gradener.clean(new Garden());
        //裁缝清洁衣服
        Cleaner tailer = new Cleaner();
        tailer.clean(new Cloth());

    }
}
