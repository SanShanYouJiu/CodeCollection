import java.util.Random;

/**
 * Created by han on 2016/11/4.
 */
public class LinkStackDemo {
    /**
     *栈结点 由于是链栈 所以是链表形式
     */
    class StackNode{
        Object data;
        StackNode next;

        @Override
        public String toString() {
            return "StackNode{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }
    }

    /**
     * 链栈 栈顶 在此操作元素
     */
    class LinkStack{
        StackNode top;
        int count;

        @Override
        public String toString() {
            return "LinkStack{" +
                    "top=" + top +
                    ", count=" + count +
                    '}';
        }
    }

    LinkStack S = new LinkStack();

    /**
     * 插入元素e为新的栈顶元素
     * @param e 插入元素
     * @return true运行成功 false运行失败
     */
    boolean Push(Object e) {
        StackNode s = new StackNode();
        s.data=e;
        s.next=S.top;/*把当前的栈顶元素赋值给新节点的直接后继*/
        S.top=s;/*将新的节点s赋值给栈顶指针 */
        S.count++;
        return  true;
    }

     /**
     * 若栈不空 则删除S的栈顶元素
     * @param e  e返回删除元素的值
     * @return ture为成功 false为失败
     */
    boolean Pop(Object e){
        if (StackEmpty())return  false;
        e=S.top.data;
        S.top=S.top.next;/*使得栈顶指针下移一位 指向后一节点 */
        S.count--;
        return  true;
    }



    /**
     * 判断是否栈空
     * @return true为栈空 false为栈不空
     */
    boolean StackEmpty() {
        if(S.top==null){
             return  true;
        }else
        return false;
    }


     void ClearStack(){
       S.top=null;
       S.count=0;
     }

    void  CreateListHead(int n){
        LinkStack p,q;
        Random random = new Random( );

        for (int i = 0; i <n ; i++) {
            StackNode s = new StackNode();
             s.data=random.nextInt(100);
             s.next=S.top;
             S.top=s;
            S.count++;
        }
    }


    public static void main(String[] args) {
        LinkStackDemo l = new LinkStackDemo();
        Object o = new Object();
        l.Pop(o);
        l.Push("ce");
        l.Push("ce");
        System.out.println(l.S);
        System.out.println(l.S.count);
        l.Pop(o);
        l.ClearStack();
        System.out.println(l.S);
        l.CreateListHead(5);
        System.out.println(l.S);
    }
}
