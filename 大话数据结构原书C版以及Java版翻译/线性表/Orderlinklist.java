/*线性表-顺序存储*/
public class Orderlinklist{
int MAXSIZE=100;


 /*
 顺序链表插入
 */
 private  boolean insert(ShuzuTest  a ,int i,Object e){
         int k;
         if(a.length==MAXSIZE){
           return false;
         }
         if (i<1||i>a.length+1) {
           return false;
         }

        if(i<a.length){
           for (k=a.length-1;k>=i-1 ;k-- ) {
              a.data[k+1]=a.data[k];
           }
        }
        a.data[i-1]=e;
        a.length++;
        return true;
  }
/*获取元素
*/
  private boolean GetElem(ShuzuTest a,int i,Object e){
    if(a.length ==0 || i<1||i>a.length)
        return false;
        e=a.data[i-1];
      System.out.println(e);
      return true;

  }
/* 删除元素
*/
 private boolean ListDelete(ShuzuTest a,int i,Object e){
   int k;
   if(a.length==0)/*线性表为空*/
     return false;

   if(i<1 || i>a.length)/*删除位置不正确*/
   return  false;

   e=a.data[i-1];
    if(i<a.length){/*如果删除位置不是最后位置*/
      for (k=i;k<a.length ;k++ ) /*将删除位置后继元素前移 填补空缺*/
        a.data[k-1]=a.data[k];//k-(k-1)
    }
     a.length--;
     return true;
 }

    public static void main(String[] args) {

        Orderlinklist ceshi = new Orderlinklist();
        ShuzuTest a = new ShuzuTest();
        Object o = new Object();
        User user = new User();
        ceshi.insert(a, 1, user);
        System.out.println(a.length);
        ceshi.GetElem(a, 1, o);
        ceshi.ListDelete(a, 1, o);
        System.out.println(a.length);

    }
}

class  User{
    private  int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
class ShuzuTest {

    int length=0;
    Object []data=new Object[100];
}
