package DesignMode.DesignModel.Strategy.EnumStrategy;

/**策略枚举模式
 * Created by han on 2017/3/1.
 */
public enum Calculator {

    ADD("+"){
        public int exec(int a, int b) {
            return a+b;
        }
    },
    SUB("-"){
        public int exec(int a, int b) {
            return a-b;
        }
    };
    String value = "";

    private Calculator(String _value) {
        this.value=_value;
    }

    public String getValue() {
        return value;
    }

    public abstract int exec(int a, int b);
}
