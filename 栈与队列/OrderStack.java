import java.util.Arrays;

/**顺序栈与 两栈共用
 * Created by han on 2016/11/3.
 */
public class OrderStack {
    int MAXSIZE=20;

    private class SqStack {

        Object[] data=new Object[MAXSIZE];
         int top=-1;/*用于栈顶指针*/

        @Override
        public String toString() {
            return "SqStack{" +
                    "data=" + Arrays.toString(data) +
                    ", top=" + top +
                    '}';
        }
    }

    private class  SqDoubleStack{
          Object[] data=new Object[MAXSIZE];
         int top1=-1;
         int top2=MAXSIZE;

        @Override
        public String toString() {
            return "SqDoubleStack{" +
                    "data=" + Arrays.toString(data) +
                    ", top1=" + top1 +
                    ", top2=" + top2 +
                    '}';
        }
    }

    SqStack S = new SqStack();
    SqDoubleStack S2 = new SqDoubleStack();

    /** 单顺序栈  压栈
     * 插入元素e为新的栈顶元素
     * @param e
     * @return
     */
   public  boolean Push(Object e){
       if(S.top==MAXSIZE-1)//栈满
       {
             return false;
       }
       S.top++;/*栈顶指针增加1*/
       S.data[S.top]=e;/*将新插入元素复制给栈顶空间*/
       System.out.println(e);
       return  true;
   }


    /**
     *俩栈共用 压栈
     * @param e
     * @param StackNumber
     * @return
     */
  public  boolean Push2(Object e,int StackNumber){
       if(S2.top1+1 == S2.top2)/*栈已满 不会push新元素*/
           return  false;
      if (StackNumber ==1)/*栈一有元素进栈*/
          S2.data[++ S2.top1]=e;
      else if (StackNumber ==2)/*栈2有元素进栈*/
          S2.data[-- S2.top2]=e;
      return  true;
  }


    /** 单顺序栈 弹栈
     * 若栈不空，则删除S的栈顶元素，删除成功返回true，否则返回FALSE
     * @param e 用E来接删除掉的值
     * @return
     */
    public  boolean Pop(Object e){
        if(S.top ==-1)
            return  false;
        e=S.data[S.top];//将要删除的栈顶元素赋值给e
        System.out.println(e);
        S.top--;//栈顶指针减一
        return true;
    }

    /**俩栈共用 弹栈
     * @param e
     * @param stackNumber
     * @return
     */
    public  boolean Pop2(Object e,int stackNumber){

        if(stackNumber ==1){
            if(S2.top1==-1)
                return  false;/*说明栈1是空的栈 溢出*/
            e=S2.data[S2.top1 --];/*将栈1的栈顶元素出栈*/
        }else if (stackNumber ==2){
            if(S2.top2==MAXSIZE)/*说明栈2是空的栈 溢出*/
                return  false;
            e=S2.data[S2.top2++];/*将栈2的栈顶元素出栈*/
        }
        return  true;
    }



    public static void main(String[] args) {
        OrderStack orderStack = new OrderStack();
        orderStack.Push(3);
        orderStack.Push(2);
        orderStack.Push(1);
        DemoEntity o = new DemoEntity();
        orderStack.Pop(o);
        orderStack.Pop(o);
        System.out.println(orderStack.S);
        System.out.println("-----------------------------------");
        orderStack.Push2(3, 1);
        orderStack.Push2(3, 2);
        System.out.println(orderStack.S2);
        orderStack.Pop2(o, 1);
        orderStack.Pop2(o, 2);
        System.out.println(orderStack.S2);

    }

}
