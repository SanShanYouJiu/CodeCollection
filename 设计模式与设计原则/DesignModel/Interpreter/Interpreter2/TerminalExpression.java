package DesignMode.DesignModel.Interpreter.Interpreter2;

/**终结者表达式
 * Created by han on 2017/3/12.
 */
public class TerminalExpression extends Expression {

    //通常终结符表达式只有一个 但是有多个对象
    @Override
    public Object interoreter(Context ctx) {
        return  null;
    }
}
