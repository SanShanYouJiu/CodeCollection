
/**
 * 对比c语言的二叉排序树翻译的版本 相比起来主要是C的指针在java中不能适用 改变了一下传参的方式
 * 在删除方法中补充了一些原本书上不全面的内容 以及为了功能的实现 加入了几个新的变量便于实现
 * Created by han on 2017/2/3.
 */
public class Binary_Sort_Tree {

    private BiTree T = null;

    private BiTree p = null;

    boolean SearchBST(BiTree T, int key, BiTree f) {

        if (T == null) {
            p = f;
            return false;
        } else if (key == T.data) {
            p = T;
            return true;
        } else if (key < T.data)
            return SearchBST(T.lchild, key, T);
        else
            return SearchBST(T.rchild, key, T);

    }


    boolean InsertBST(int key) {
        BiTree s;
        if (!SearchBST(T, key, null)) {
            s = new BiTree();
            s.data = key;
            s.lchild = s.rchild = null;
            if (p == null) {
                T = s;
            } else if (key < p.data) //插入为左孩子
                p.lchild = s;
            else //插入为右孩子
                p.rchild = s;
            return true;
        } else
            return false;
    }

    //从二叉排序树中删除结点p 并重接它的左或右子树
    boolean Delete(BiTree p) {
        BiTree q, s = null;
        if (p.rchild == null && p.lchild == null) {
            if (lastCallParameterFlag == 1) {
                Flag.lchild = null;
            } else {
                Flag.rchild = null;
            }
        } else if ((p).rchild == null) { //右子树空则只需重连它的左子树
            p.data = p.lchild.data;
            p.lchild = p.lchild.lchild;
        } else if ((p).lchild == null) {//只需重连它的右子树
            p.data = p.rchild.data;
            p.rchild = p.rchild.rchild;
        } else {//左右子树均不空
            q = p;
            s = p.lchild;
            while (s.rchild != null) {
                q = s;
                s = s.rchild;
            }
            (p).data = s.data;
            if (q != p)
                q.rchild = s.lchild;
            else
                q.lchild = s.lchild;
        }
        return true;
    }

    BiTree Flag;
    int lastCallParameterFlag = 0;

    //若二叉排序树T中存在关键字key的数据元素时 则删除该数据元素结点
    //并返回TRUE 否则返回FALSE
    boolean DeleteBST(BiTree T, int key) {

        if (T == null)
            return false;
        else {
            if (key == (T).data)
                return Delete(T);
            else if (key < (T).data) {
                lastCallParameterFlag = 1;
                Flag = T;
                return DeleteBST(T.lchild, key);
            } else {
                lastCallParameterFlag = 2;
                Flag = T;
                return DeleteBST(T.rchild, key);
            }
        }
    }


    public static void main(String[] args) {
        Binary_Sort_Tree binary_sort_tree = new Binary_Sort_Tree();
        int[] a = {62, 88, 58, 47, 35, 73, 51, 99, 37, 93};
        for (int i = 0; i < a.length; i++)
            binary_sort_tree.InsertBST(a[i]);
        System.out.println(binary_sort_tree.T);
        binary_sort_tree.SearchBST(binary_sort_tree.T, 93, null);
        System.out.println("寻找到了 :" + binary_sort_tree.p);
        System.out.println("---------------------------------------------");
        binary_sort_tree.DeleteBST(binary_sort_tree.T, 47);//删除某项节点
        System.out.println(binary_sort_tree.T);

    }

    class BiTree {

        public BiTree() {
        }

        public BiTree(Integer data) {
            this.data = data;
        }

        Integer data = null;
        BiTree lchild, rchild;

        @Override
        public String toString() {
            return "BiTree{" +
                    "data=" + data +
                    ", lchild=" + lchild +
                    ", rchild=" + rchild +
                    '}';
        }
    }

}
