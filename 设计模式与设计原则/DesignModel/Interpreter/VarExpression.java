package DesignMode.DesignModel.Interpreter;

import java.util.HashMap;

/**变量解析器
 * Created by han on 2017/3/12.
 */
public class VarExpression extends Expression {
  private String key;

    public VarExpression(String key) {
        this.key = key;
    }

    //从map中取值
    public int interpreter(HashMap<String, Integer> var) {
        return var.get(key);
    }

}

