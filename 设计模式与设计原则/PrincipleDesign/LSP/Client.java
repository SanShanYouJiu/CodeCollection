package DesignMode.PrincipleDesign.LSP;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by han on 2017/1/14.
 */
public class Client {

    public static  void invoker() {
        Son f = new Son();
//        Father f = new Father() ;
        HashMap map = new HashMap();
        f.dosomething(map);

    }




    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException {
        invoker();


        /*反射加载方法*/
        Class sClass = Class.forName("com.Test.DesignMode.PrincipleDesign.LSP.Son");
        Object co = sClass.newInstance();
        Method[] methods = null;
        for (Method m:sClass.getDeclaredMethods()) {
            m.invoke(co, new HashMap());
            System.out.println(m.getName());
        }
    }


    /*利用反射编写良好的类数组*/

    public static Object goodCopyof(Object a, int newLength) {
        Class cl = a.getClass();
        if(!cl.isArray()) return null;
        Class componenType = cl.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componenType, length);
          System.arraycopy(a,0,newArray,0,Math.min(length,newLength));
        return newArray;
    }



    public static  Object[] badCopy(Object[] a,int newlength) {
        Object[] newArray = new Object[newlength];
        System.arraycopy(a, 0, newArray, 0, Math.min(a.length, newlength));
        return newArray;
    }


}
