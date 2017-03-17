package DesignMode.DesignModel.Iterator.Iterator2;

/**抽象迭代器
 * Created by han on 2017/3/3.
 */
public interface Iterator {
    //遍历到下一个元素
    public Object next();

    //是否已经访问到尾部
    public boolean hasNext();

    //删除当前指向的元素
    public  boolean remove();
}
