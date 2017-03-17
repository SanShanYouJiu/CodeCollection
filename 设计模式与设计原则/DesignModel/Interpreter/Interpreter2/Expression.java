package DesignMode.DesignModel.Interpreter.Interpreter2;

/**
 * Created by han on 2017/3/12.
 */
public  abstract class Expression {
    //每个表达式必须有一个解析任务
    public abstract Object interoreter(Context ctx);
}
