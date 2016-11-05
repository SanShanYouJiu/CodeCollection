/**
 * Created by han on 2016/11/5.
 */
public class LinkListQueue {

  private   class QNode{
        Object data;
        QNode next;

      @Override
      public String toString() {
          return "QNode{" +
                  "data=" + data +
                  ", next=" + next +
                  '}';
      }
  }

   private   class  LinkQueue{
         QNode front,rear;/*front是队头 rear是队尾*/

       public LinkQueue() {
           front = new QNode();
           rear = new QNode();
           front=rear;
       }

       @Override
       public String toString() {
           return "LinkQueue{" +
                   "front=" + front +
                   ", rear=" + rear +
                   '}';
       }
   }

    LinkQueue Q = new LinkQueue();

    /**
     * 插入元素e为新元素
     * @param e
     * @return
     */
    boolean EnQueue( Object e) {
        QNode s = new QNode();
        s.data=e;
        s.next = null;
        Q.rear.next=s;//把拥有元素的s放到原队尾的后继
        Q.rear=s;//把当前s设置为队尾结点 rear指向s
        return true;
    }


    /**
     * 若队列不空，则删除Q的队头元素 用o存储其值，并返回true，否则返回false
     * @param o
     * @return
     */
    boolean DeQueue(Object o){
        QNode p;
        if(Q.front ==Q.rear)/*如果为空 就抛出false*/
            return false;
        p=Q.front.next;/*将删除的元素暂存给p*/
        o=p.data;
        Q.front.next=p.next;/*将原队头结点后继p.next赋值给头结点后继*/
        if(Q.rear==p)/*若队头是队尾 则删除后将rear指向队头*/
             Q.rear=Q.front;
        return  true;
    }



    int length(){
        QNode p;
        int count=0;
        p = Q.front;
        System.out.println(p);
        while (p.next !=null) {
            count++;
            p = p.next;
        }
        return count;
    }



    public static void main(String[] args) {
        LinkListQueue l = new LinkListQueue();
        l.EnQueue("ce");
        l.DeQueue("c");
        System.out.println(l.length());

    }
}
