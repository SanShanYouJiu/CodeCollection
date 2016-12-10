
import java.util.Random;

/** 循环单链表  这个Demo经测试已完成   但是toString打印就会导致无限递归出错
 * 但是内部已经形成指针类似的环
 * Created by han on 2016/10/30.
 */
public class CycleLinkList {

    public static void main(String[] args) {
        LinkList linkList = new LinkList();
        linkList.insertNode("ce1",1);
        linkList.insertNode("ce01", 1);
        linkList.insertNode("ce2", 3);
        linkList.GetElem(2);
        linkList.insertNode("ce02", 3);
        linkList.insertNode("ce4", 5);
        linkList.insertNode("ce3", 6);

        System.out.println("当前链表长度为：" + linkList.length());
        linkList.GetElem(1);
        linkList.delNode(1);
        linkList.delNode(2);
        linkList.GetElem(1);
        linkList.GetElem(2);
        System.out.println("当前链表长度为：" + linkList.length());
        linkList.GetElem(2);
        linkList.GetElem(3);
        System.out.println("------------------------------");
        linkList.CreateListHead(5);
        System.out.println("------------------------------");
        linkList.CreateListTail(5);
        linkList.GetElem(12);
        System.out.println("--------------------------------");
        linkList.GetElem(1);
        linkList.GetElem(5);
        linkList.GetElem(9);
        linkList.GetElem(10);
        linkList.GetElem(11);
        linkList.GetElem(12);
        linkList.GetElem(13);
        linkList.GetElem(14);
        linkList.ClearList();
        linkList.iterator();
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

       /* @Override//如果你要打印的话就产生了无限递归  因为是环
               public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }*/
    }

    private    Node head=new Node();//头结点


    public LinkList() {
        head.next = this.head;
        this.head = head.next;
    }//头结点在空结点时next的值是自身

    /*获取指定元素*/
    public  boolean GetElem(int i){
        int j;
        Node p; /*声明一指针p*/
        if(head ==null || i>length() )return  false;
        p=head.next; /*让p指向链表L的第一个结点*/
        j=1;
        while (p!=head && j<i){/*p不为空且计算器j还没有等于i时，循环继续*/
            p=p.next;/*让p指向下一个结点*/
            ++j;
        }
        if(p==head||j>i)/*第i个结点不存在*/
            return false;
//        p.data;/*取第i个结点的数据*/·
        System.out.println("Get到的数据"+p.data);
        return true;
    }



    /*插入指定位置*/
    public  boolean insertNode(Object o,int index){

        int i=1;
        Node n=new Node(o);
        Node preNode = head;//从头结点开始
        Node curNode = head.next;//设置这个变量为了循环记忆使用
        while (true) {

            if (i == index) {
                //这个步骤就是为了在prenode与curnode中插入节点 下面同理
                n.next = curNode;
                preNode.next = n;
                return true;
            }
            i++;
            preNode = curNode;
            curNode = preNode.next;
        }
    }


    /*删除指定节点*/
    public boolean delNode(int index){
        if(index <1||index >length()){
            return false;
        }
        int i=1;//计数器
        Node preNode=head;
        Node curNode=preNode.next;//设置这个变量为了循环记忆使用
        while (curNode!=null) {//从头结点向下循环寻找
            if(i==index){
                preNode.next=curNode.next;
                return true;
            }
            i++;
            preNode=curNode;
            curNode=preNode.next;
        }
        return true;
    }


    /*返回链表长度*/
    public int length(){
        int length=0;//计数器
        if (head.next == head) return  0;
        Node p=head.next;
        while (p!=head) {
            length++;
            p=p.next;
        }
        return length;
    }


    /*随机生成n个元素的值 建立带表头节点的单链线性表
     始终让头结点在第一个位置 头插法 头结点为空
    */
    public void CreateListHead( int n) {
        ++n;//因为不能插入头结点（p[0]） 所以通过自增来减掉偏差
        Node [] p=new Node[n];//要插入的节点
        int i;
        p[0]=head;//带头结点的单链表
        Random random = new Random( );

        for (i = 1; i < n; i++) {
            int  randomint=random.nextInt(100);
            p[i] = new Node(randomint);
            System.out.println("插入顺序"+"i:["+i+"]"+"\t"+"CreateListHead:"+randomint);
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
        r= new Node();//初始化
        Node prenode=head;
        Node curnode = head.next;
        for (int j = 0; j <length() ; j++) {//遍历到当前最后一个结点
            curnode=prenode.next;
            prenode = curnode;
        }
        for (i = 0; i <n ; i++) {
            int rondomInt = random.nextInt(100);
            p = node[i] = new Node(rondomInt);// 每次都生成一个新节点:node[i] 随机填充数据
            r.next = p;// 将尾结点指针定位到当前结点
            p.next = head;
            r = p;// 将当前结点定义为新节点  （只是r的位置被改变 值本身还在原来的node数组中 r代表了尾结点）
            curnode.next = r;//在这里形成环
            prenode = curnode.next;
            curnode = prenode;
            System.out.println("插入顺序" + "i:[" + i + "]" + "\t" + "CreateListTail:" + rondomInt);
        }


        for ( i = n-1; i >=0 ; i--) {
            System.out.println( "node[" + i + "]:"+"CreateListTail:" + node[i]  );
        }

        System.out.println("CreateListTail当前链表长度为："+length());

    }

    /*迭代当前链表所有元素*/
    public  void iterator(){
        Node iteratorNode=head.next;
        Node iteratorNode2=iteratorNode.next;
        for (int ii = 2; ii <length() ; ii++) {
            iteratorNode=iteratorNode2;
            iteratorNode2=iteratorNode.next;
            System.out.println(iteratorNode.data);
        }
    }



    /*初始条件：顺序线性表L已存在 操作结果 ：将L重置为空表*/
    public  void ClearList(){
        head.next=head;
        head=head.next;

    }


    /*与C语言的主要处理方式是一样的 这个方法就没有进行完善*/
    public void mergeLinkList(LinkList L,LinkList L2){
        LinkList rearA,rearB,p,q;

    }


}
