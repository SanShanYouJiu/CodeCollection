/**
 * 允许插入的为队尾 删除的为队头
 * Created by han on 2016/11/5.
 */
public class OrderQueue {
  int MAXSIZE=5;
    class SqQueue{
        Object [] data=new Object[MAXSIZE];
        int front;//前指针
        int rear;//尾指针 若队列不空 指向队列尾元素的下一个位置
    }

    /**
     * 初始化一个空队列
     */
    SqQueue Q = new SqQueue();

         OrderQueue(){
          Q.front=0;
          Q.rear=0;
     }

    /**
     * 返回Q的元素个数 也就是链表的当前长度
     * @return
     */
    int QueueLength() {
        return (Q.rear - Q.front + MAXSIZE) % MAXSIZE;
    }

    /**
     * 若队列未满 则插入元素e
     * @param e 插入元素
     * @return
     */
    Boolean EnQueue(Object e){
        if((Q.rear+1)%MAXSIZE ==Q.front)
            return  false;
        Q.data[Q.rear]=e;
        System.out.println(e);
        Q.rear=(Q.rear+1)%MAXSIZE;
        return  true;
    }

    /**
     * 若队列不空 则Q队头元素
     * @param o
     * @return
     */
    Boolean Dequeue(Object o){
        if((Q.front ==Q.rear))
            return  false;
        o=Q.data[Q.front];
        System.out.println(o);
        Q.front=(Q.front+1)%MAXSIZE;
         return  true;
    }

    Boolean ClearQueue(){
        Q.front=0;
        Q.rear=0;
        return  true;
    }

    Object GetElement(int n) {
        Object o = new Object();
        o = Q.data[n];
        int s=(Q.front - Q.rear + MAXSIZE) % MAXSIZE;

        return  o;
    }

    public static void main(String[] args) {
        OrderQueue orderQueue = new OrderQueue();
        orderQueue.EnQueue("ce1");
        orderQueue.EnQueue("ce2");
        orderQueue.EnQueue("ce3");
        System.out.println(orderQueue.QueueLength());
        orderQueue.Dequeue("");
        System.out.println(orderQueue.QueueLength());
    }

}
