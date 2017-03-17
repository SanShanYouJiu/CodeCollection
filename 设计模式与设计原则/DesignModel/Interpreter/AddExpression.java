package DesignMode.DesignModel.Interpreter;

import java.util.HashMap;

/**
 * Created by han on 2017/3/12.
 */
public class AddExpression extends SymbolExpression{

    public AddExpression(Expression _left, Expression _right) {
        super(_left, _right);
    }

    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return super.left.interpreter(var) + super.right.interpreter(var);
    }
}
