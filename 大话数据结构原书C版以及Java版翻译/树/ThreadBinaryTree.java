/**
 * 中序线索化
 * Created by han on 2016/11/26.
 */
public class ThreadBinaryTree {

    class BiThrNode{
        Object data;// 结点数据
        BiThrNode lchild=null,rchild=null;//左右孩子指针
        Boolean Ltag=false;
        Boolean Rtag=false; //左右标示

        public BiThrNode(Object data) {
            this.data = data;
        }

        public BiThrNode() {
        }

        @Override
        public String toString() {
            return "BiThrNode{" +
                    "data=" + data +
                    ", lchild=" + lchild +
                    ", rchild=" + rchild +
                    ", Ltag=" + Ltag +
                    ", Rtag=" + Rtag +
                    '}';
        }
    }
  BiThrNode pre=new BiThrNode();




    /**
     * 中序遍历进行中序线索化
     * @param p
     */
    void  InThreading(BiThrNode p){

        if(p!=null){
            InThreading(p.lchild);
            if(p.lchild==null ){
               p.Ltag=true;
               p.lchild=pre;
            }

            if(pre.rchild==null){
             pre.Rtag=true;
             pre.rchild=p;
            }
            pre=p;
            InThreading(p.rchild);
        }
    }


    /**
     *T指向头结点 头结点左链lchild指向根节点 头结点右链rchild指向中序遍历的
      最后一个结点 中序遍历二叉线索链表表示的二叉树T
     * @param T
     * @return
     */
     boolean  InorderTraverse_Thr(BiThrNode T) {
         BiThrNode p;
         p=T.lchild;
         while (p!=T)
         {
              while (p.Ltag==false)
                  p=p.lchild;
                  System.out.println(p.data);
                  while (p.Rtag==true&&p.rchild!=T){
                      p=p.rchild;
                      System.out.println(p.data);
                  }
                  p=p.rchild;
         }
         return true;
     }


    /**
     *
     * @param T 二叉树
     * @param directry 方向 true为左 false为右
     * @param data 要插入的数据
     * @throws Exception
     */
    BiThrNode add(BiThrNode T,boolean directry,Object data) throws Exception {
        if(T==null)
            throw  new Exception("结点为空");

        if(directry) {
            T.lchild = new ThreadBinaryTree().new BiThrNode(data);
            return T.lchild;
        }else {
            T.rchild = new ThreadBinaryTree().new BiThrNode(data);
            return  T.rchild;
        }
    }




    public static void main(String[] args) throws Exception {
        BiThrNode biThrNode = new ThreadBinaryTree().new BiThrNode();
        ThreadBinaryTree tree = new ThreadBinaryTree();


        BiThrNode L1=  tree.add(biThrNode, true, "L");
        BiThrNode m1= tree.add(L1, false, "R");
        BiThrNode L2= tree.add(m1, true, "L2");
        BiThrNode m2=   tree.add(L2, false, "R2");
        BiThrNode L3= tree.add(m2, true, "L3");
        BiThrNode m3= tree.add(L3, false, "R3");
        System.out.println(biThrNode);
        tree.InThreading(biThrNode);
        tree.InorderTraverse_Thr(biThrNode);

    }
}
