/**因为L.r[0]无意义 作为临时常量处理 在前面的冒泡 直接插入 选择排序中过于简单就没有设置这个变量的作用
 *  但是在希尔排序中的L.r的Length长度全部减一 因为因为java内部的数组访问是在有0的情况下 而长度则是总长度 按C语言
 *  方案处理会出现数组越界异常
 *
 */
public class ShellSort {
    private static final int Max = 10;

    class SqList {
        int[] r = new int[Max];
        int length = r.length;
    }


    void ShellSort(SqList L) {
        int i, j;
        int increment = L.length-1;
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
        L.r = new int[]{0,10, 2, 4, 6, 11, 3, 5, 7,8, 9,1};//L.r[0]数据用作哨兵 不存值 这里使用0进行占位符作用
        L.length = L.r.length;
        sort.ShellSort(L);
        for (int i = 1; i < L.length; i++) {//L.r[0]数据用作哨兵 数据本身无意义
            System.out.println(L.r[i]);
        }
    }
}
