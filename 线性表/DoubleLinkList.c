#include <stdio.h>/*双向链表 个人所补充 在只需要一个方向的指针的方法中 双向链表与单链表的操作并无区别 ，java版本是双向循环链表
注意：同单循环链表一样 我对C语言的语法已经不熟悉了
推荐看注释 或者java版本
 */
#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
typedef int ElemType;/*ElemType根据实际情况而定 这里假设为int*/
typedef int Status;
typedef struct DulNode
{
ElemType data;
struct DulNode *prior;/*直接前驱指针*/
struct DulNode *next;/*直接后继指针*/
}DulNode,*DuLinkList;


Status GetElem(DuLinkList L,int i,ElemType *e) {
	int j;
	DuLinkList p; /*声明一指针p*/
	p=L->next; /*让p指向链表L的第一个结点*/
	j=1; /*J为计数器*/
	while(p && j<i) { /*p不为空且计算器j还没有等于i时，循环继续*/
		p=p->next;/*让p指向下一个结点*/
		++j;
	}
	if(!p|| j>i)
		return ERROR;/*第i个结点不存在*/
	*e=p->data;/*取第i个结点的数据*/
	return OK;
}

Status ListInsert(DuLinkList *L,int i,ElemType e) {
	int j;
	DuLinkList p,s;
	p= *L;//获取头结点
	j=1;
	while(p && j<i) { /*寻找第i-1个结点*/
		p=p->next;
		++j;
	}

	if(!p || j>i)
		return ERROR;

	s=(DuLinkList)malloc(sizeof(DulNode));/* 生成新节点(C标准函数) sizeof操作符以字节形式给出了其操作数的存储大小*/
	s->data=e;
	s->prior=p;/*把p赋值给s的前驱*/
	s->next=p->next; /*将p->next赋值给s的后继*/
	p->next->prior=s;/*把s赋值给p->next的前驱*/
	p->next=s;/*将S赋值给p的后继*/
	return OK;

}


Status ListDelete(DuLinkList *L,int i,ElemType *e) {
	int j;
	DuLinkList p;
	p=*L;//获取头结点
	j=1;

	while(p->next && j<i) { /*遍历寻找第i-1个结点*/
		p=p->next;
		++j;
	}

	if(!(p->next) || j>i)
		return ERROR;/*第i个结点不存在*/
    p=p->next;
	p->prior->next=p->next;/*把p->next赋值给p->prior的后继*/
	p->next->prior=p->prior; /*把p->prior赋值给p->next的前驱*/
	free(p); /*让系统回收此节点 释放内存*/
	return OK;
}
