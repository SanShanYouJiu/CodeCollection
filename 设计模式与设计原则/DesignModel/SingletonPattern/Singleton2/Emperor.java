package DesignMode.DesignModel.SingletonPattern.Singleton2;

import java.util.ArrayList;
import java.util.Random;

/**有上限的单例模式  可以在设计时决定游民多少个实例 方便系统进行扩展修正单例可能存在的性能问题 提供系统的响应速度
 * Created by han on 2017/1/19.
 */
public class Emperor {
    //最多实例数量
    private static int maxNumOfEmperor = 2;
    //皇帝名字列表
    private static ArrayList<String> namelist = new ArrayList<String>();
    //皇帝实例列表
    private static ArrayList<Emperor> emperorArrayList = new ArrayList<Emperor>();
    //当前皇帝序号
    private static int  countNumOfEmperor =0;
    //初始化皇帝实例
    static {
        for (int i=0;i<maxNumOfEmperor;i++) {
            emperorArrayList.add(new Emperor("皇" + (i + 1) + "帝"));
        }
    }
    private Emperor(){

    }

    private Emperor(String name) {
        namelist.add(name);
    }

    //返回一个随机皇帝
    public static  Emperor getInstance() {
        Random random = new Random();
        countNumOfEmperor = random.nextInt(maxNumOfEmperor);
        return emperorArrayList.get(countNumOfEmperor);
    }

    public static void say(){
        System.out.println(namelist.get(countNumOfEmperor));
    }

}
