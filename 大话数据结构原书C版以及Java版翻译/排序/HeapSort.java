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
        for (i = (L.length-1) / 2; i > 0; i--) {
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
        L.r = new int[]{0,1, 2, 4, 6, 7, 3, 5, 8, 9,10,0,11};// L.r[0]数据用作哨兵 不存值 这里使用0暂时存值
        L.length = L.r.length;
        sort.HeapSort(L);
        for (int i = 0; i < L.length; i++) {  //  经过变化后L.r[L.length-1]的元素无意义
            System.out.println(L.r[i]);
        }
    }
}
