/**KMP 字符串匹配算法
 * Created by han on 2016/11/7.
 */
public class StringTest {


    public static void main(String[] args) {
        int[] next = getNext("ABCDABD");
        for (int i = 0; i <next.length ; i++) {
            System.out.println("next[" + i + "]=" + next[i]);
        }
    }


    public static int[] getNext(String ps) {

        char[] p = ps.toCharArray();

        int[] next = new int[p.length];

        next[0] = -1;

        int j = 0;

        int k = -1;

        while (j < p.length - 1) {

            if (k == -1 || p[j] == p[k]) {

                if(p[++j] ==p[++k]){
                    next[j]=next[k];
                }else{
                    next[j]=k;
                }

            } else {

                k = next[k];

            }
        }
        return next;

    }

    public  static  int KMP(String ts,String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        int i=0;//主串的位置
        int j=0;//模式串的位置
        int[] next = getNext(ps);
        while (i < t.length && j < p.length) {
            if(j ==-1 || t[i] ==p[j]){// 当j为-1时 要移动的是i 当然j也要归0
                i++;
                j++;
            }else {
                //i不需要回溯了
                //i=i-j+1;
                j = next[j];//回溯到指定位置
            }
        }
        if (j == p.length){
            return i-j;
        }else{
            return -1;
        }

    }
}
