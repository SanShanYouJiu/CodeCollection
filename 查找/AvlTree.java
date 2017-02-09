
/**AVL树
 * Created by han on 2017/2/7.
 */
public class AvlTree {

    private final static int LH = 1;
    private final static int EH = 0;
    private final static int RH = -1;


    BiTNode T = new BiTNode();

    Boolean taller;
    BiTNode TempT;
    int lastFlag;

    BiTNode RotateTempT;
    /**
     * lastRotateFlag 65535代表oo不具备意义 0代表在左平衡系统中作为暂存变量(代表为左结点前驱)
     * 1代表在右平衡系统中作为暂存变量(代表为右结点前驱)
     * 2代表在SearchBST中作为在T中寻找子树的暂存变量(代表为左结点前驱)
     * 3代表在SearchBST中作为在T中寻找子树的暂存变量(代表为右结点前驱)
     *
     */
    int lastRotateFlag=65535;


    /**
     * 在P为T子树的情况下
     * 寻找P在T中的位置 用RotateTempT代表前驱
     * @param P
     * @param T
     * @return
     */
    boolean SearchBST(BiTNode P, BiTNode T) {
        if (T.data == P.data) {
            return true;
        } else if (P.data > T.data) {//右子树寻找
            RotateTempT = T;
            lastRotateFlag=3;
            return SearchBST(P, T.rchild);
        }
        else {//左子树寻找
            RotateTempT=T;
            lastRotateFlag=2;
            return SearchBST(P, T.lchild);
        }
    }

    /**
     * 右转
     *
     * @param P
     */
    void R_Rotate(BiTNode P) {
        BiTNode L;
        L = P.lchild;
        P.lchild = L.rchild;
        L.rchild = P;
        if (P.data==T.data)
            T=L;
        else if (lastRotateFlag==1) {
            RotateTempT.rchild = L;
            lastRotateFlag=65535;
        }else {
            SearchBST(P,T);
            if (lastRotateFlag==2) {
                RotateTempT.lchild = L;
                lastRotateFlag=65535;
            }else if (lastRotateFlag==3){//可以省略
                RotateTempT.rchild=L;
                lastRotateFlag=65535;
            }
        }
    }

    /**
     * 左转
     *
     * @param P
     */
    void L_Rotate(BiTNode P) {
        BiTNode R;
        R = P.rchild;
        P.rchild = R.lchild;
        R.lchild = P;
        if (P.data==T.data)
            T=R;
        else  if (lastRotateFlag==0) {
            RotateTempT.lchild = R;
            lastRotateFlag=65535;
        }else {
            SearchBST(P,T);
             if (lastRotateFlag==2) {//可以省略
                 RotateTempT.lchild = R;
                 lastRotateFlag=65535;
             }else if (lastRotateFlag==3){
                 RotateTempT.rchild=R;
                 lastRotateFlag=65535;
             }
        }
    }

    /**
     * 左平衡处理
     *
     * @param T
     */
    void LeftBalance(BiTNode T) {
        BiTNode L, Lr;
        L = T.lchild;
        switch (L.bf) { //检测L的平衡度
            case LH:
                T.bf = L.bf = EH;
                R_Rotate(T);
                break;
            case RH:
                Lr = L.rchild;
                switch (Lr.bf) {
                    case LH:
                        T.bf = RH;
                        L.bf = EH;
                        break;
                    case EH:
                        T.bf = L.bf = EH;
                        break;
                    case RH:
                        T.bf = EH;
                        L.bf = LH;
                        break;
                }
                Lr.bf = EH;
                RotateTempT=T;//旋转暂时变量
                lastRotateFlag=0;
                L_Rotate(T.lchild);
                R_Rotate(T);
        }

    }



    /**
     * 右平衡处理
     *
     * @param T
     */
    void RightBalance(BiTNode T) {
        BiTNode R, RL;
        R = T.rchild;
        switch (R.bf) {
            case RH:
                T.bf = R.bf = EH;
                L_Rotate(T);
                break;
            case LH:
                RL = R.lchild;
                switch (RL.bf) {
                    case LH:
                        T.bf = LH;
                        RL.bf = EH;
                        break;
                    case EH:
                        T.bf = R.bf = EH;
                        break;
                    case RH:
                        T.bf = EH;
                        RL.bf = RH;
                        break;
                }
                RL.bf = EH;
                RotateTempT=T;//旋转暂时变量
                lastRotateFlag=1;
                R_Rotate(T.rchild);
                L_Rotate(T);
        }
    }



     Boolean InsertAVL(BiTNode T, int e) {
         if (T==null||(T.data == null && T.lchild == null && T.rchild == null)  ) {
             if (T==null) {
              if (lastFlag==0)
                 T= TempT.lchild = new BiTNode();
              else
                  T = TempT.rchild = new BiTNode();
             }
             T.data = e;
             T.lchild = T.rchild = null;
             T.bf = EH;
             taller = true;
         } else {
             if (e == T.data) {
                 taller = false;
                 return false;
             }
             if (e < T.data) {
                 TempT = T;
                 lastFlag = 0;
                 if (!InsertAVL(T.lchild, e))
                     return false;
                 if (taller) {
                     switch (T.bf) {
                         case LH:
                             LeftBalance(T);
                             taller = false;
                             break;
                         case EH:
                             T.bf = LH;
                             taller = true;
                             break;
                         case RH:
                             T.bf = EH;
                             taller = false;
                             break;
                     }
                 }
             } else {
                 TempT = T;
                 lastFlag = 1;
                 if (!InsertAVL(T.rchild, e))
                     return false;
                 if (taller) {
                     switch (T.bf) {
                         case LH:
                             T.bf = EH;
                             taller = false;
                             break;
                         case EH:
                             T.bf = RH;
                             taller = true;
                             break;
                         case RH:
                             RightBalance(T);
                             taller = false;
                             break;
                     }
                 }
             }
         }
         return true;
     }


    public static void main(String[] args) {
        AvlTree avlTree = new AvlTree();
        int i;
        int[] a = {3, 2, 1, 4, 5, 6, 7, 10, 9, 8};
        BiTNode T = null;
        for (i = 0; i < a.length; i++) {
          avlTree.InsertAVL(avlTree.T, a[i]);
        }
        System.out.println(avlTree.T);
    }

    class BiTNode {
        Integer data;
        int bf;
        BiTNode lchild, rchild;

        @Override
        public String toString() {
            return "BiTNode{" +
                    "data=" + data +
                    ", bf=" + bf +
                    ", lchild=" + lchild +
                    ", rchild=" + rchild +
                    '}';
        }
    }
}
