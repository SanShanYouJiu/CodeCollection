/**
 *  在堆排序中的L.r的Length长度全部减一 因为java内部的数组访问是在有0的情况下 而长度则是总长度
 *  并不像像C语言中处理 (:C语言中长度是自我赋值 存在可调性  因为在本书前言中L.r[0]是作临时变量处理
 *  我在JAVA版则省略了临时变量这一步
 */
public class HeapSort {
    private static final int Max = 10;

    class SqList {
        int[] r = new int[Max];
        int length = r.length;
    }

    //交换
    void swap(SqList L, int i, int j) {
        int temp=L.r[i];
        L.r[i]=L.r[j];
        L.r[j]=temp;
    }


    void HeapSort(SqList L) {
        int i;
        for (i = (L.length-1) / 2; i > -1; i--) {
          HeapAdjust(L,i,L.length-1);
        }
        for (i = L.length-1; i > 0; i--) {
            swap(L, 0, i);
            HeapAdjust(L,0,i-1);
        }
    }

    void HeapAdjust(SqList L, int s, int m) {
           int temp,j;
           temp = L.r[s];
        for (j = 2 * s; j < m; j *= 2) {

            if (j < m && L.r[j] < L.r[j+1]) {
                ++j;
            }
            if (temp>=L.r[j])
                break;
            L.r[s]=L.r[j];
            s=j;
        }
        L.r[s]=temp;
    }



    public static void main(String[] args) {
        HeapSort sort = new HeapSort();
        SqList L = sort.new SqList();
        L.r = new int[]{17,1, 2, 4, 6, 7, 3, 5, 8, 9,10,0};
        L.length = L.r.length;
        sort.HeapSort(L);
        for (int i = 0; i < L.length ; i++) {
            System.out.println(L.r[i]);
        }
    }
}
