package DesignMode.DesignModel.Iterator.Iterator2;

/**抽象容器
 * Created by han on 2017/3/3.
 */
public interface Aggregate {
    //容器必然有元素的增加
    public void add(Object object);

    //减少元素
    public void remove(Object object);

    //由迭代器来遍历所有的元素
    public Iterator iterator();

}
