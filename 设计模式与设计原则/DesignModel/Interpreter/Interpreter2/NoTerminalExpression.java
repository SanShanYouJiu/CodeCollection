package DesignMode.DesignModel.Interpreter.Interpreter2;

/**非终结者表达式
 * Created by han on 2017/3/12.
 */
public class NoTerminalExpression extends Expression {

    //每个非终结者表达式都会对其他表达式产生依赖
    public NoTerminalExpression(Expression... exepressions) {

    }

    @Override
    public Object interoreter(Context ctx) {
        //进行文法处理
        return null;
    }
}
