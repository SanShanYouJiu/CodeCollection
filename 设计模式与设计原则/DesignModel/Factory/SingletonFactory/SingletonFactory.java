package DesignMode.DesignModel.Factory.SingletonFactory;

import java.lang.reflect.Constructor;

/**工厂方法创建单例
 * Created by han on 2017/1/19.
 */
public class SingletonFactory {

    private static  Singleton  singleton;
    static {
        try{
            Class c1= Class.forName(Singleton.class.getName());
            Constructor constructor = c1.getDeclaredConstructor();
            constructor.setAccessible(true);
            singleton = (Singleton)constructor.newInstance();
        }catch (Exception e){
            //异常处理
        }
    }


    public  static  Singleton getSingleton(){
        return singleton;
    }
}
