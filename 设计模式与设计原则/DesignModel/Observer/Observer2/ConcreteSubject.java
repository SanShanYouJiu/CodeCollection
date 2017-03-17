package DesignMode.DesignModel.Observer.Observer2;

/**
 * Created by han on 2017/3/6.
 */
public class ConcreteSubject extends Subject {

    //具体的业务
    public void doSomething(){
        /**
         * do Something
          */
        super.notifyObservers();
    }
}
