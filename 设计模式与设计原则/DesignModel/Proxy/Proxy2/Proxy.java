package DesignMode.DesignModel.Proxy.Proxy2;

/**
 * Created by han on 2017/2/22.
 */
public class Proxy implements Subject {


    //要代理哪个实现类
    private Subject subject = null;

    //默认被代理者
    public Proxy() {
        this.subject = new Proxy();
    }


    //通过构建函数传递代理者
    public Proxy(Object... objects) {

    }

    public  Proxy(Subject _subject) {
        this.subject = _subject;
    }


    @Override
    public void request() {
        this.before();
        this.subject.request();
        this.after();
    }

   //预处理
    private void before() {
        //do something
    }

    //善后处理
    private void after() {
        //do something
    }
}
