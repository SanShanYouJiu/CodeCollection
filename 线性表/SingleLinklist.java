
import java.util.Random;

/**C语言与JAVA语言内部有很多差别 所以看起来有点不一样 ， java语言实现的功能较C语言多了一些 并且java内部有GC的原因 在清理内存的时候会方便很多
 * 从零开始或者从1开始的方法很简单 只需要将下面的计数器与判断是否头结点的全部从0改为1就好 length的计数器不用改
 * 线性表-单链表
 * Created by han on 2016/10/27.
 */
public class SingleLinklist {
    int MAXSIZE=20;


    public static void main(String[] args) {
        LinkList linkList = new LinkList();
        User user = new User();
        linkList.insertNode("ce1",1);
        linkList.insertNode("ce01", 1);
        linkList.insertNode("ce2", 3);
        linkList.insertNode("ce02", 3);
        linkList.insertNode("ce4", 5);
        linkList.insertNode("ce3", 6);

        System.out.println("当前链表长度为：" + linkList.length());
        linkList.GetElem(1);
        linkList.GetElem(2);
        linkList.GetElem(3);
        linkList.GetElem(4);
        linkList.GetElem(5);
        linkList.delNode(1);

        System.out.println("当前链表长度为：" + linkList.length());
        linkList.GetElem(2);
        linkList.GetElem(3);
        System.out.println("------------------------------");
        linkList.CreateListHead(5);
        System.out.println("------------------------------");
        linkList.CreateListTail(5);
        linkList.GetElem(12);
        System.out.println("--------------------------------");
        linkList.iterator();

        linkList.ClearList();
        linkList.GetElem(1);
    }
}
class LinkList{
    private   class Node{
        //结点数据
        Object data;
        //指向下一个结点指针
        Node next;
        public Node(){	}
        public Node(Object data ){
            this.data=data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }
    }

    private  Node head=null;//头结点
    private  Node tail = null;//尾结点

     /*获取指定元素*/
    public  boolean GetElem(int i){
        int j;
        Node p; /*声明一指针p*/
        p=head; /*让p指向链表L的第一个结点*/
        j=1;
        while (p!=null && j<i){/*p不为空且计算器j还没有等于i时，循环继续*/
            p=p.next;/*让p指向下一个结点*/
            ++j;
        }
        if(p==null||j>i)/*第i个结点不存在*/
            return false;
//        p.data;/*取第i个结点的数据*/·
        System.out.println("Get到的数据"+p.data);
        return true;

    }

    /*防止插入头结点情况*/
    public void addNode(Object d){
        Node newNode = new Node(d);
        int i=1;
        if(head == null){  //空链表，插入为头指针
            head = newNode;
        }else{
            Node p = head;
            newNode.next=p;
            head=newNode;
            System.out.println("p:"+p+"newNode:"+newNode);
        }
    }

    /*插入指定位置*/
    public  boolean insertNode(Object o,int index){
        if(index==1){
            addNode(o);
            return  true;
        }

        int i=1;
        Node n=new Node(o);
        Node preNode = head;//从头结点开始
        Node curNode = head.next;//设置这个变量为了循环记忆使用
              while (true) {
                  i++;
                  if (i == index) {
                      //这个步骤就是为了在prenode与curnode中插入节点 下面同理
                      preNode.next = n;
                      n.next = curNode;
                      return true;
                  }
                  preNode = curNode;
                  curNode = preNode.next;
              }
      }


   /*删除指定节点*/
    public boolean delNode(int index){
        if(index <1||index >length()){
            return false;
        }
        //删除链表第一个节点
        if(index ==1){
            head=head.next;//head的下一个节点赋值给head本身 数据丢失 删除完毕
            return true;
        }else{
            //不是第一个节点的情况下
            int i=1;//计数器
            Node preNode=head;
            Node curNode=preNode.next;//设置这个变量为了循环记忆使用
            while (curNode!=null) {//从头结点向下循环寻找

                if(i==index){
                    preNode.next=curNode.next;
                    return true;
                }
                preNode=curNode;
                curNode=preNode.next;
                i++;
            }
        }
        return true;
    }


    /*返回链表长度*/
    public int length(){
        int length=0;//计数器
        Node p=head;
        while (p!=null) {
            length++;
            p=p.next;
        }
        return length;
    }

    /*随机生成n个元素的值 建立带表头节点的单链线性表
     始终让头结点在第一个位置 头插法 头结点为空
    */
    public void CreateListHead( int n) {
        ++n;//因为p[0]要被当做头结点  所以通过自增来减掉偏差
        Node [] p=new Node[n];//要插入的节点
        int i;
        p[0]=head;//保持头结点为空
        Random random = new Random( );

        for (i = 1; i < n; i++) {
            p[i] = new Node(random.nextInt(100));
            System.out.println("插入顺序"+"i:["+i+"]"+"\t"+"CreateListHead:"+p[i]);
            p[i].next = p[0].next;
            p[0].next = p[i];//插入到表头
        }
        System.out.println("p[0]:" + p[0]);
        for (int j = 1; j < n; j++) {
            System.out.println("p["+j+"]"+p[j]);
        }

        System.out.println("当前链表长度为："+length());
    }


    /*随机产生n个元素的值 建立起带表头结点的单链线性表  尾插法*/
    public  void  CreateListTail(int n){
        Node [] node=new Node[n];//要插入的资源
        Node p,r;
        int i;
        Random random = new Random( );
        r= node[n-1] = new Node( );// r为指向尾部的节点

        for (i = 0; i <n ; i++) {
            p = node[i] = new Node(random.nextInt(100));// 每次都生成一个新节点:node[i] 随机填充数据
            r.next = p;// 将尾结点指针定位到当前结点
            r=p;// 将当前结点定义为新节点  （只是r的位置被改变 值本身还在原来的node数组中 r代表了尾结点）
            System.out.println( "插入顺序"+"i:[" + i + "]"+"\t"+"CreateListTail:" + r  );
        }
        r.next = tail;//表示当前链表结束


        /*将数据添加到当前链表中*/
        Node prenode=head;
        Node curnode = head.next;
        for (int j = 2; j <length() ; j++) {//元素需要保留一个 再加上一个prenode与curnode之间的缓冲
            prenode = curnode;
            curnode=prenode.next;
        }
         curnode.next=node[0];


        for ( i = n-1; i >=0 ; i--) {
            System.out.println( "node[" + i + "]:"+"CreateListTail:" + node[i]  );
        }


        System.out.println("当前链表长度为："+length());

    }

    /*迭代当前链表所有元素*/
      public  void iterator(){
          Node iteratorNode=head.next;
          Node iteratorNode2=iteratorNode.next;
          for (int ii = 2; ii <length() ; ii++) {
              iteratorNode=iteratorNode2;
              iteratorNode2=iteratorNode.next;
              System.out.println(iteratorNode);
          }
      }

    /*初始条件：顺序线性表L已存在 操作结果 ：将L重置为空表*/
    public  void ClearList(){

        head=null;
        System.out.println(length());
     }


}
