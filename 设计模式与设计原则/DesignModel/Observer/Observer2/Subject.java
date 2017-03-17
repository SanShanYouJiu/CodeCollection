package DesignMode.DesignModel.Observer.Observer2;


import java.util.Vector;

/**
 * Created by han on 2017/3/6.
 */
public abstract class Subject {
    private Vector<Observer> observers = new Vector<Observer>();

    //增加一个观察者
    public void addObserver(Observer o) {

    }

    //删除一个观察者
    public void delObserver(Observer o){

    }

    //通知所有观察者
    public  void notifyObservers() {
        for (Observer o : this.observers) {
            o.update();
        }
    }
}
