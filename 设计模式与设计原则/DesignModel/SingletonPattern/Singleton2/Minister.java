package DesignMode.DesignModel.SingletonPattern.Singleton2;

/**
 * Created by han on 2017/1/19.
 */
public class Minister {


    public static void main(String[] args) {
        int ministerNum =5;
        for (int i=0;i<ministerNum;i++){
            Emperor emperor =Emperor.getInstance();
            System.out.println("第"+(i+1)+"个大臣参拜的是：");
            Emperor.say();
        }
    }
}
