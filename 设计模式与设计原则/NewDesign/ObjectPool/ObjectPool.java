package DesignMode.NewDesign.ObjectPool;

import java.util.Hashtable;

/**
 * Created by han on 2017/3/30.
 */
public abstract class ObjectPool<T> {
    //容器，容纳对象
    private Hashtable<T, ObjectStatus> pool = new Hashtable<T, ObjectStatus>();

    //初始化时创建对象 并放入池中
    public ObjectPool() {
        pool.put(create(),new ObjectStatus());
    }

    //从Hashtable中取出空闲元素
    public synchronized T checkout() {
        //这是最简单的策略
        for (T t : pool.keySet()) {
            if (pool.get(t).validate()) {
                pool.get(t).setUsing();
                return t;
            }
        }
        return null;
    }

    //归还对象
    public synchronized void checkin(T t) {
        pool.get(t).setFree();
    }

  class  ObjectStatus{
    //占用
     public void setUsing() {

     }

     //释放
     public void setFree() {
         //注意：若T是有状态 则需要回到初始化状态
     }

     //检测是否有用
     public boolean validate() {
         return false;
     }

 }
    //创建池化对象
    public abstract T create();
}



