/**冒泡排序
 * Created by han on 2017/4/1.
 */
public class Bubbing {

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

 //冒泡排序初级版 交换排序
    void BubbleSort0(SqList L) {
        int i,j;
        for ( i = 0; i <L.length ; i++) {
            for (j=i+1;j<L.length;j++) {
                if (L.r[i] > L.r[j]) {
                    swap(L,i,j);
                }
            }
        }
    }


    //冒泡排序
    void BubbleSort(SqList L) {
        int i,j;
        for (i = 1; i < L.length; i++) {
            for (j = L.length - 2; j >= i; j--) {
                if (L.r[j] > L.r[j+1]) {
                    swap(L,j,j+1);
                }
            }
        }
    }

    void BubbleSort2(SqList L) {
        int i, j;
        boolean flag = true;
        for (i = 1; i < L.length&&flag; i++) {
            flag=false;
            for (j = L.length - 2; j >= i; j--) {
                if (L.r[j] > L.r[j + 1]) {
                    swap(L,j,j+1);
                    flag=true;
                }
            }
        }
    }


    public static void main(String[] args) {
        SqList L = new Bubbing().new SqList();
        L.r = new int[]{1, 2, 4, 6, 0, 3, 5, 8, 9};
        L.length=L.r.length;
        Bubbing bubbing = new Bubbing();
        bubbing.BubbleSort(L);
        for (int i = 0; i < L.length; i++) {
            System.out.println(L.r[i]);
        }
    }
}
