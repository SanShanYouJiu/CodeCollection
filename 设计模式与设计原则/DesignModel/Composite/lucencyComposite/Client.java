package DesignMode.DesignModel.Composite.lucencyComposite;

/**场景类
 * Created by han on 2017/3/4.
 */
public class Client {
    public static void main(String[] args) {
        //创建一个根节点
        Composite root = new Composite();
        root.doSomething();
        //创建一个树枝构件
        Composite branch = new Composite();
        //创建一个叶子节点
        Leaf leaf = new Leaf();
        //建立整体
        root.add(branch);
        branch.add(leaf);
    }
  //通过递归遍历树  在透明模式下 遍历整个树形结构是比较容易的 不用进行强制类型转换
    public static void display(Component root) {
        for (Component c : root.getChildren()) {
            if (c instanceof Leaf) {//叶子节点
                c.doSomething();
            }else {//树枝结点
                display(c);
            }
        }
    }
}
