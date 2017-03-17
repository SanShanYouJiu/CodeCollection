package DesignMode.DesignModel.Interpreter;


/**
 * Created by han on 2017/3/12.
 */
public  abstract  class SymbolExpression extends Expression {
   protected  Expression left;
   protected  Expression right;

    public SymbolExpression(Expression _left, Expression _right) {
        this.left=_left;
        this.right=_right;
    }

}
