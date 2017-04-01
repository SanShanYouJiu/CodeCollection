
/**简单选择排序
 * Created by han on 2017/4/1.
 */
public class SimpleSelectOrder {

    private static  final int Max=10;

    class SqList {
        int[] r=new int[Max];
        int length=r.length;
    }

    //交换
    void swap(SqList L, int i, int j) {
        int temp=L.r[i];
        L.r[i]=L.r[j];
        L.r[j]=temp;
    }


    //简单选择排序
    void SelectSort(SqList L) {
      int i,j,min;
        for (i = 0; i <L.length-1 ; i++) {
         min=i;
            for (j = i + 1; j < L.length-1; j++) {
                if (L.r[min] > L.r[j]) {
                    min=j;
                }
            }
            if (i != min) {
                swap(L,i,min);
            }
        }
    }

    public static void main(String[] args) {
        SimpleSelectOrder selectOrder = new SimpleSelectOrder();
        SqList L = selectOrder.new SqList();
        L.r = new int[]{1, 2, 4, 6, 0, 3, 5, 8, 9};
        L.length = L.r.length;
        selectOrder.SelectSort(L);
        for (int i = 0; i <L.length ; i++) {
            System.out.println(L.r[i]);
        }
    }

}
