#include <stdio.h>
#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
typedef int ElemType;/*ElemType根据实际情况而定 这里假设为int*/
/*线性表的单链表存储结构  --循环链表  个人所改 这本书内部只是大概介绍了一下 注意 我个人对C语言的语法忘的差不多了
推荐看注释
或者看java版本来进行理解
只有一个Demo 在mergeLinkList
 需要注意 对错我是没有试验过的 bug应该有 只是通过了编译
 例行上传
*/
typedef int Status;
typedef struct Node {
	ElemType data;
	struct Node *next;
} Node;

typedef struct Node *LinkList;/*定义LinkList*/



//创建单向循环链表  初始化时，头结点的指针指向自己 注意：这个方法是正确的 这是经过测试的
LinkList CreateSgCcLinkList(void)
{
   int i,length=0,data=0;
   LinkList pTail=NULL,p_new=NULL;
   LinkList pHead=(LinkList)malloc(sizeof(Node));

    if(NULL == pHead){
    	printf("内存分配失败!\n");
    	exit(0);
	}
	pHead->data=0;
	pHead->next=pHead;//空节点状态 头结点指向自己
	pTail=pHead;//同理 空结点也是头结点
	 printf("请输入要创建链表的长度:");
	 scanf("%d",&length);

	 for(i=1;i<length+1;i++)
	 {
	 p_new=(LinkList)malloc(sizeof(Node));
	  if(NULL ==p_new){
	  	printf("内存分配失败! \n");
	  	exit(0);
	  }
	  printf("请输入第%d个结点的元素值："+i);
	  scanf("%d",&data);

	  p_new->data=data;
	  p_new->next=pHead;//最后一个结点总是指向头结点
	  pTail->next=p_new;//尾插法
	  pTail=p_new;
	  }

	  return pHead;

}

/*
初始条件：顺序线性表L已存在 1<=i<=ListLength(L)
操作条件：用e返回L中第i个数据元素的值
*/
Status GetElem(LinkList L,int i,ElemType *e) {
	int j;
	LinkList p,r; /*声明一指针p*/
	r=L;/*头结点 0结点*/
	p=L->next; /*让p指向链表L的第一个结点*/
	j=1; /*J为计数器*/
	while(p!=r && j<i) { /*p不为空且计算器j还没有等于i时，循环继续*/
		p=p->next;/*让p指向下一个结点*/
		++j;
	}
	if(p=r|| j>i)
		return ERROR;/*第i个结点不存在*/
	*e=p->data;/*取第i个结点的数据*/
	return OK;
}

/*
初始条件 ：顺序线性表L已存在 1<=i<=ListLength(L)
操作条件：在L中第i个结点位置之前插入新的数据元素e,l的长度加1
*/
Status ListInsert(LinkList *L,int i,ElemType e) {
	int j;
	LinkList p,s,r;
	r= *L;//头结点
	p=r->next;
	j=1;
	while(p!=r && j<i) { /*寻找第i-1个结点*/
		p=p->next;
		++j;
	}

	if(p=r || j>i)
		return ERROR;

	s=(LinkList)malloc(sizeof(Node));/* 生成新节点(C标准函数) sizeof操作符以字节形式给出了其操作数的存储大小*/
	s->data=e;
	s->next=p->next; /*将p的后继节点赋值给s的后继*/
	p->next=s;/*将S赋值给p的后继*/
	return OK;

}
/*
 初始条件：顺序线性表L已存在，1<=i<=ListLength(L)
 操作结果: 删除L的第i个结点 并用E返回其值 L的长度减1
 由于是循环链表  所以是不能等于头结点
*/
Status ListDelete(LinkList *L,int i,ElemType *e) {
	int j;
	LinkList p,q,r;
	r=*L;//头结点
	p=r->next;
	j=1;

	while(p->next!=r && j<i) { /*遍历寻找第i-1个结点*/
		p=p->next;
		++j;
	}

	if((p->next)=r || j>i)
		return ERROR;/*第i个结点不存在*/
	q=p->next;
	p->next=q->next;/*将q的后继赋值给p的后继*/
	*e=q->next;  /*将q结点中的数据给e**/
	free(q); /*让系统回收此节点 释放内存*/
	return OK;
}



/*随机生成n个元素的值 建立带表头节点的单链线性表*/
Status CreateListHead (LinkList *L,int n) {
	LinkList p,phead;
	int i;
	srand(time(0));/*初始化随机数种子*/
	*L=(LinkList)malloc(sizeof(Node));
	phead=(*L);/*先建立一个带头结点的单链表 这里理解（*L）是头结点*/
  phead->next=phead;/*空结点时 头结点本身是头结点*/

	for(i=0; i<n; i++) {

		p=(LinkList)malloc(sizeof(Node));/*生成新节点*/
		p->data=rand()%100+1;/*随机生成100以内的数字*/
		p->next=(*L)->next;
		(*L)->next=p;/*插入到表头*/

	}

}

/*循环链表特殊功能 合并俩个不同的链表*/
void mergeLinkList(LinkList *L,LinkList *L2){
LinkList rearA,rearB,p,q;
   rearA=*L;/*获取L尾结点   注意:我也不知道这样是否正确获取到*/
   rearB=*L2;/*获取L2尾结点 同上*/
   p=rearA->next;/*保存A表的头结点*/
   rearA->next=rearB->next->next; /*将本是指向B表的第一个结点（不是头结点）赋值给rearA->next*/
   q=rearB->next;
   rearB->next=p;
   free(q);/*释放q*/
}





/*随机产生n个元素的值 建立起带表头节点的单链线性表（尾插法）*/
void CreateListTail(LinkList *L,int n) {
	LinkList p,r,s;
	int i;
	srand(time(0));/*初始化随机数种子*/
	*L=(LinkList)malloc(sizeof(Node));/*为整个线性表*/
	r=*L;/*r此时为指向尾部的节点*/

	for(i=0; i<n; i++) {
		p=(Node *)malloc(sizeof(Node));/*生成新节点*/
		p->data=rand() %100+1;/*随机生成100以内的数字*/
		r->next=p;/*将表尾终端节点的指针指向新节点*/
		p->next=(*L);  //尾结点的下一个节点指向头结点
		r=p;//成为新的尾结点
		s=*L;//当前最后一个结点
		s->next=r;// 形成环
	}
	r->next=(*L);/*表示当前链表结束 尾结点的下一个是头结点*/

}



/*初始条件：顺序线性表L已存在 操作结果 ：将L重置为空表*/
Status ClearList(LinkList *L) {
	LinkList p,q,r;
	r=(*L);/*头结点*/
	p=(*L)->next; /*p指向第一个结点*/

	while(p!=r) {  /*没到表尾*/
		q=p->next;
		free(p);
		p=q;
	}
	(*L)->next=r;/*头结点指针域为头结点本身*/
	return OK;
}


//个人所加 并没有测试功能
int length(LinkList *L){
	int length=0;
	LinkList p=(*L);//获得头结点
	LinkList q=p->next;
	while(q!=p){
		length++;
		q=q->next;
	}
	return length;
}


void  main() {

	LinkList list,start;
	printf("请输入单链表的数据");
	list=CreateListHead(list,10);
	for(start =list->next; start!=NULL; start=start->data)
		printf("%d",start->data);
	print("/n");



}
