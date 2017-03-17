package DesignMode.DesignModel.Iterator.Iterator2;

import java.util.Vector;

/**具体容器
 * Created by han on 2017/3/3.
 */
public class ConcreteAggregate implements Aggregate {

    //容纳对象的容器
    private Vector vector = new Vector();

    //增加一个元素
    @Override
    public void add(Object object) {
        this.vector.add(object);
    }

    //返回迭代器对象
    @Override
    public Iterator iterator() {
        return new ConcreteIterator(this.vector);
    }

    //删除一个元素
    @Override
    public void remove(Object object) {
        this.vector.remove(object);
    }



}
