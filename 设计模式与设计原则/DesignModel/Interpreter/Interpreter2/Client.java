package DesignMode.DesignModel.Interpreter.Interpreter2;


import java.util.Stack;

/**
 * Created by han on 2017/3/12.
 */
public class Client {
    public static void main(String[] args) {
        Context ctx = new Context();
        //通常定一个语法容器  容纳一个具体的表达式  通常为ListArray LinkedList Stack等类型
        Stack<Expression> stack =null;
        for (int i=1;i<100;i++){//假设为100
            //进行语法判断 并产生递归调用
        }
        //产生一个完整的语法树 由各个具体的语法分析进行解析
        Expression exp = stack.pop();
        //具体元素进入场景
        exp.interoreter(ctx);
    }
}
