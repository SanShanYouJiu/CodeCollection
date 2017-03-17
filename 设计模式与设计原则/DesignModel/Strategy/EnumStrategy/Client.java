package DesignMode.DesignModel.Strategy.EnumStrategy;

import java.util.Arrays;

/**
 * Created by han on 2017/3/1.
 */
public class Client {

    public static final String ADD_SYMBOL = "+";
    public static final String SUB_SYMBOL = "-";

    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        String symbol = args[1];
        int b = Integer.parseInt(args[2]);
        System.out.println("输入的参数：" + Arrays.toString(args));
        System.out.println("运行结果为：" + a + symbol + b + "=" + Calculator.ADD.exec(a, b));
    }
}
