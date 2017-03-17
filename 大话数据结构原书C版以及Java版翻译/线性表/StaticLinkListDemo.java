/**
 * 具体注释在C语言代码中 只是翻译下
 * 线性表-静态链表 （游标实现法） 这个是为了在原本没有指针比如basic/future 这些的早期高级语言中实现的一种链表方法
 * Created by han on 2016/10/29.
 */
public class StaticLinkListDemo {

    int MAXSIZE;

    public StaticLinkListDemo(int MAXSIZE) {
        this.MAXSIZE = MAXSIZE;
    }

    boolean InitList(StaticLinkList[] space){
        int i;
        for(i=0;i<MAXSIZE-1;i++) {
            space[i] = new StaticLinkList();//这里有些特别 java需要自己初始化
            space[i].cur=i+1;
        }
            space[MAXSIZE - 1] = new StaticLinkList();//这里同上 初始化
            space[MAXSIZE-1].cur=0;
            return  true;
    }

       /*若备用空间链非空 则返回分配的节点下标，否则返回0*/
    int Malloc_SLL(StaticLinkList[] space){
        int i=space[0].cur;
        if (space[0].cur!=0)
            space[0].cur=space[i].cur;
        return  i;
    }

      Boolean ListInsert(StaticLinkList[] L,int i,Object e){
          int j,k,l;
          k=MAXSIZE-1;
          if(i<1 || i>ListLength(L)+1)
              return  false;
          j=Malloc_SLL(L);
          if(j!=0)
          {
              L[j].data=e;
              for(l=i;l<i-1;l++)
                  k=L[k].cur;
              L[j].cur=L[k].cur;
              L[k].cur=j;
              return  true;
          }
       return  false;
      }


    Boolean ListDelete(StaticLinkList[] L,int i)
    {
         int j,k;
        if(i<1||i>ListLength(L))
            return  false;
        k=MAXSIZE-1;
        for ( j = 0; j <i-1 ; j++)
            k=L[k].cur;
            j=L[k].cur;
            L[k].cur=L[j].cur;
            Free_SSL(L,j);
            return  true;
    }

    /*将下标为k的空闲节点回收到备用链表*/
    void Free_SSL(StaticLinkList[] space,int k)
    {
        space[k].cur=space[0].cur;
        space[0].cur=k;
    }


     int ListLength(StaticLinkList[] L){
         int j=0;
         int i=L[MAXSIZE-1].cur;
         while (i!=0){
             i=L[i].cur;
             j++;
         }
         return  j;
     }


    public static void main(String[] args) {
        StaticLinkListDemo staticLinkListDemo = new StaticLinkListDemo(1000);
        StaticLinkList[] staticLinkList=new StaticLinkList[1000];
        staticLinkListDemo.InitList(staticLinkList);

        staticLinkListDemo.ListInsert(staticLinkList, 1, "cc");
        staticLinkListDemo.ListInsert(staticLinkList, 1, "cc2");
        System.out.println(staticLinkList[1]);
        System.out.println(staticLinkList[2]);
        System.out.println(staticLinkListDemo.ListLength(staticLinkList));
        staticLinkListDemo.ListDelete(staticLinkList, 1);
        System.out.println(staticLinkListDemo.ListLength(staticLinkList));
    }

}
class StaticLinkList{
        Object data;
        int cur;

    @Override
    public String toString() {
        return "StaticLinkList{" +
                "data=" + data +
                ", cur=" + cur +
                '}';
    }
}