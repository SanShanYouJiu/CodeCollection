/**直接插入排序
 * Created by han on 2017/4/1.
 */
public class DirectInsertOrder {

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


    void InsertSort(SqList L){
       int i,j;
        for (i = 2; i < L.length; i++) {
            if (L.r[i] < L.r[i - 1]) {
                L.r[0] = L.r[i];
                for (j=i-1;L.r[j]>L.r[0];j--)
                    L.r[j+1]=L.r[j];
                L.r[j+1]=L.r[0];
            }
        }
    }

    public static void main(String[] args) {
        DirectInsertOrder insertOrder = new DirectInsertOrder();
        SqList L = insertOrder.new SqList();
        L.r = new int[]{1, 2, 4, 6, 0, 3, 5, 8, 9};
        L.length = L.r.length;
        insertOrder.InsertSort(L);
        for (int i = 0; i <L.length ; i++) {//L.r[0]数据用作哨兵 数据本身无意义
            System.out.println(L.r[i]);
        }
    }

}
