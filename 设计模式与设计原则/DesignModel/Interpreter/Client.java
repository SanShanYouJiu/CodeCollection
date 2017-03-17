package DesignMode.DesignModel.Interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by han on 2017/3/12.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        String expstr = getExpStr();
        //赋值
        HashMap<String, Integer> var = getValue(expstr);
        Calculator cal = new Calculator(expstr);
        System.out.println("运算结果为："+expstr+"expStr"+cal.run(var));
    }

    //获得表达式
    public static  String getExpStr() throws IOException {
        System.out.println("输入表达式");
        return (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }

    //获得值映射
    public static HashMap<String,Integer> getValue(String  expStr) throws IOException {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        //解析几个参数要传递
        for (char ch:expStr.toCharArray()){
            if (ch!='+'&&ch!='-') {
                //解决重复参数的问题
                if (!map.containsKey(String.valueOf(ch))) {
                     String in=(new BufferedReader(new InputStreamReader(System.in))).readLine();
                     map.put(String.valueOf(ch), Integer.valueOf(in));
                }
            }
        }
        return map;
    }
}
