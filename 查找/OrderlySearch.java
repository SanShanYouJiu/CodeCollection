
/**顺序表查找
 * Created by han on 2017/2/1.
 */
public class OrderlySearch {

     //顺序查找
    int Sequential_Search(int[] a,int n,int key){
        int i;
        for (i=1;i<=n;i++){
            if (a[i]==key)
                return i;
        }
        return 0;
    }

    //带哨兵顺序的查找
    int Sequential_Search2(int[] a,int n,int key){
        int i;
        a[0]=key;
        i=n;
        while (a[i]!=key){
            i--;
        }
        return i;
    }

    //折半查找
   int Binary_Search(int[] a,int n,int key){
       int low,high,mid;
       low=1;
       high = n;
       while (low<=high){
           mid=(low+high)/2;
           if(key<a[mid])
               high=mid-1;
           else if(key>a[mid])
               low=mid+1;
           else
               return mid;
       }
       return 0;
    }


    //插值查找
    int Binary_Search2(int[] a,int n,int key){
        int low,high,mid;
        low=1;
        high=n;
        while (low<=high){
            mid=low+(high-low)*(key-a[low])/(a[high]-a[low]);
            if (key<a[mid])
                high=mid-1;
            else  if (key<a[mid])
                low=mid+1;
            else
                return  mid;
        }
        return 0;
    }

    //斐波那契数列
    int[] F = new int[40];

    int Fib(int i){
        if (i<2)
            return i==0 ?0:1;
        return Fib(i-1)+Fib(i-2);
    }

    void FibDemo() {
        for (int i = 0; i < F.length; i++) {
            F[i]=Fib(i);
        }
    }

    //比折半查找好在不使用除法  斐波那契查找的运行时间理论上比折半查找小，但是还是得视具体情况而定。
    int Fibonacci_Search(int[] a, int n, int key) {
        int low,high,mid,i,k;
        low=1;
        high=n;
        k=0;
        while (n<F[k]-1)//计算n位于斐波那契数列的位置
            k++;
        for (i=n;i<F[k]-1;i++)
            a[i]=a[n];
        while (low<=high){
            mid=low+F[k-1]-1; //分割的位置
            if (key<a[mid]){ //查找的记录小于当前分割记录
                high=mid-1; //最高下标调整为下标mid-1处
                k=k-1; //斐波那契数列下标减一位
            }
            else if (key>a[mid]){//查找的记录大于当前分割记录
                low=mid+1; //最低下标调整为mid+1处
                k=k-2;//斐波那契数列下标减2位
            }else{
                if (mid<=n)
                    return mid;//若相等则说明mid即为查找到的位置
                else
                    return n;//若mid>n 则是补全位置 返回n
            }
        }
        return 0;
    }




    public static void main(String[] args) {
        OrderlySearch binarySearch = new OrderlySearch();
        int[] a ={0,1,16,24,35,47,59,62,73,88,99};
        int locate=  binarySearch.Binary_Search(a,10,62);
        System.out.println(locate);
        binarySearch.FibDemo();


    }
}
