public class ShellSort {
    private static final int Max = 10;

    class SqList {
        int[] r = new int[Max];
        int length = r.length;
    }


    void ShellSort(SqList L) {
        int i, j;
        int increment = L.length;
        do {
            increment = increment / 3 + 1;
            for (i = increment + 1; i <= L.length-1; i++) {
                if (L.r[i] < L.r[i - increment]) {
                    L.r[0] = L.r[i];
                    for (j = i - increment; j > 0 && L.r[0] < L.r[j]; j -= increment)
                        L.r[j + increment] = L.r[j];
                    L.r[j + increment] = L.r[0];
                }
            }
        } while (increment > 1);
    }



    public static void main(String[] args) {
        ShellSort sort = new ShellSort();
        SqList L = sort.new SqList();
        L.r = new int[]{0,10, 2, 4, 6, 0, 3, 5, 8, 9,1};//L.r[0]数据用作哨兵 不存值 这里使用0暂时存值
        L.length = L.r.length;
        sort.ShellSort(L);
        for (int i = 0; i < L.length; i++) {//L.r[0]数据用作哨兵 数据本身无意义
            System.out.println(L.r[i]);
        }
    }
}
