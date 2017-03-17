package DesignMode.DesignModel.Interpreter;

import java.util.HashMap;

/**
 * Created by han on 2017/3/12.
 */
public abstract class Expression {

    public abstract int interpreter(HashMap<String, Integer> var);
}
