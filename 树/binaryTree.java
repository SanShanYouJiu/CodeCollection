
import java.util.Scanner;

/** 由于java与C在类与指针方面使用的不同 暂时没能想到C语言指针的空指针空内存的效果
 * 所以稍加改变
 * Created by han on 2016/11/23.
 */
public class binaryTree {
    BiTree T;

    public binaryTree(BiTree t) {
        T = t;
    }

    public binaryTree() {
    }


    /**
     * 二叉树定义
     */
    class BiTree{
        public BiTree() {
        }

        public BiTree(Object data) {
            this.data = data;
        }

        Object data;
        BiTree lchild,rchild;

        @Override
        public String toString() {
            return "BiTree{" +
                    "data=" + data +
                    ", lchild=" + lchild +
                    ", rchild=" + rchild +
                    '}';
        }
    }


    /**
     * 二叉树的前序遍历算法
     * @param T
     */
   void PreOrderTraverse(BiTree T){
          if(T.data==null)
              return;
       System.out.println(T.data);
       PreOrderTraverse(T.lchild);
       PreOrderTraverse(T.rchild);
   }


    /**
     * 二叉树的中序遍历递归算法
     * @param T
     */
    void InOrderTraverse(BiTree T){
        if(T.data==null)
            return;
        InOrderTraverse(T.lchild);
        System.out.println(T.data);
        InOrderTraverse(T.rchild);
    }

    /**
     * 二叉树的后序遍历递归算法
     * @param T
     */
    void PostOrderTraverse(BiTree T){
         if(T.data==null)
             return;
        PostOrderTraverse(T.lchild);
        PostOrderTraverse(T.rchild);
        System.out.println(T.data);
    }


    /**
     *
     * @param T 二叉树
     * @param directry 方向 true为左 false为右
     * @param data 要插入的数据
     * @throws Exception
     */
      BiTree add(BiTree T,boolean directry,Object data) throws Exception {
          if(T==null)
              throw  new Exception("结点为空");

          if(directry) {
              T.lchild = new binaryTree().new BiTree(data);
              return T.lchild;
          }else {
              T.rchild = new binaryTree().new BiTree(data);
              return  T.rchild;
          }
      }

    void   CreatBiTree(BiTree T){
        Object ch;
        Scanner s = new Scanner(System.in);
        ch = s.next();

        if(ch.equals("#")) {
            T = null;
        }
        else {
            if(T==null)
                return;
            T.data=ch;
            CreatBiTree(T.lchild=new BiTree());
            CreatBiTree(T.rchild=new BiTree());

        }
    }


    public static void main(String[] args) throws Exception {
        binaryTree b = new binaryTree();
        BiTree T = new binaryTree().new BiTree();
//            BiTree T2 =b.add(T,true,"h");
//            BiTree W2 =b.add(T,false,"W1");
//            BiTree W3 =b.add(W2,true,"W2");
//            BiTree W4 =b.add(W3,true,"W3");
//            BiTree T3 =b.add(T2,true,"h1");
//            BiTree T4 =b.add(T3,true,"h2");
//            BiTree T5 =b.add(T4,true,"h3");

           b.CreatBiTree(T);
        System.out.println(T);
        b.PreOrderTraverse(T);
        b.PostOrderTraverse(T);
    }


}
