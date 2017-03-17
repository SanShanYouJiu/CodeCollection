package DesignMode.DesignModel.SingletonPattern;

/**线程不安全的单例
 * Created by han on 2017/1/19.
 */
public class Sigleton {
 private  static  Sigleton sigleton=null;

    private Sigleton(){

    }

    public static  Sigleton getIngleton(){
        if(sigleton ==null) {
            sigleton = new Sigleton();
        }
        return  sigleton;
    }

}

