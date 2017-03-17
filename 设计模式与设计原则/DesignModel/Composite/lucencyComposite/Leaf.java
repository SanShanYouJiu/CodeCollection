package DesignMode.DesignModel.Composite.lucencyComposite;

import java.util.ArrayList;

/**树叶构件
 * Created by han on 2017/3/4.
 */
public class Leaf extends Component {
    @Override
    public void add(Component component) {
        //空实现  抛弃不支持异常
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Component component) {
        //空实现 抛弃不支持异常
        throw new UnsupportedOperationException();
    }

    @Deprecated//这个注解在编辑期告诉调用者 你可以调用这个 但是可能会出现错误
    public ArrayList<Component> getChildren() {
        //空实现 抛弃不支持异常
        throw new UnsupportedOperationException();
    }
    /**
     * 可以覆写父类方法
     * public void doSomething(){
     *
     * }
     */
}
