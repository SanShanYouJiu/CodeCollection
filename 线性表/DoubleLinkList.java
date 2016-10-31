
/**
 * 双向循环链表 c语言版是双向链表
 * Created by han on 2016/10/31.
 */
public class DoubleLinkList {

    private class  DulNode{
        Object data;
        DulNode prior;
        DulNode next;

        public DulNode(Object data) {
            this.data = data;
        }

        public DulNode() {
        }
    }
    private  DulNode head=new DulNode();

    public DoubleLinkList() {
        head.next=head;
        head.prior=head;
        head.next.prior=head;
        head.prior.next=head;
    }

    /*获取指定元素*/
    public  boolean GetElem(int i) {
        int j;
        DulNode p; /*声明一指针p*/
        p = head.next; /*让p指向链表L的第一个结点*/
        j = 1;
        while (p != head && j < i) {/*p不为空且计算器j还没有等于i时，循环继续*/
            p = p.next;/*让p指向下一个结点*/
            ++j;
        }
        if (p == head || j > i)/*第i个结点不存在*/
            return false;
//        p.data;/*取第i个结点的数据*/·
        System.out.println("Get到的数据" + p.data);
        return true;
    }



    /*插入指定位置  方法二 这是以前习惯的写法 */
    public  boolean insertNode2(Object o,int index){
        int i=1;
        DulNode n=new DulNode(o);
        DulNode preNode = head;//从头结点开始
        DulNode curNode = head.next;//设置这个变量为了循环记忆使用
        while (true) {

            if (i == index) {
                //这个步骤就是为了在prenode与curnode中插入节点 下面同理
                n.prior=preNode;
                n.next=curNode;
                curNode.prior= n;
                preNode.next = n;

                return true;
            }
            i++;
            preNode = curNode;
            curNode = preNode.next;
        }
    }

    public  boolean insertNode(Object o,int i){
        int j=1;
         DulNode p;
         DulNode n=new DulNode(o);
         p=head;
         j=1;
        while ( j<i){//寻找i-1个结点
           p=p.next;
           ++j;
        }

        if( j>i)
            return  false;
        n.prior=p;/*把p赋值给n的前驱*/
        n.next=p.next;/*将p->next赋值给n的后继*/
        p.next.prior= n;/*把n赋值给p->next的前驱*/
        p.next = n;/*将n赋值给p的后继*/
        return  true;

    }


    public  boolean ListDelete(int i){
        int j;
        DulNode p;
        p=head;
        j=1;
        while (p.next!=head && j<i)//查找i-1个结点
        {
            p=p.next;
            ++j;
        }
        if(p.next==head || j>i)// 空结点
            return  false;
        p=p.next;
        p.prior.next=p.next;/*把p->next赋值给p->prior的后继*/
        p.next.prior=p.prior;/*把p->prior赋值给p->next的前驱*/
        return  true;
    }




    public static void main(String[] args) {
        DoubleLinkList doubleLinkList = new DoubleLinkList();
        doubleLinkList.insertNode("c", 1);
        doubleLinkList.insertNode("c2", 1);
        doubleLinkList.insertNode("c3", 2);
        doubleLinkList.insertNode("c01", 1);

        doubleLinkList.GetElem(1);
        doubleLinkList.ListDelete(1);
        System.out.println("删除了一号元素");
        doubleLinkList.GetElem(1);
        doubleLinkList.GetElem(2);
        doubleLinkList.GetElem(3);
    }

}
