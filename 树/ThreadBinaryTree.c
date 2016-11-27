#include <stdio.h>/*二叉线索树*/
#define OK 1
#define OVERFLOW 0
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define  Link 0
#define Thread 1 
#define MAX_TREE_SIZE 100 /*存储空间初始化分配量*/
typedef int TElemType;/*树结点的数据类型 目前暂定为整形*/
typedef int Status;
 BiThrTree pre;/*全局变量 始终指向刚访问过的节点*/ 
  
 /*二叉树的二叉树线索存储结构定义*/ 
typedef enum{
Link,Thread
} PointerTag;/*Link==0表示左右孩子指针*/
              /*Thread==1表示指向前驱或后继的线索*/  
  
typedef  struct BiThrNode /*二叉线索树存储结点结构*/
{
	TElemType data; /*结点数据*/ 
	struct BiThrNode *lchild,*rchlid;/*左右孩子指针*/
     PointerTag LTag;
	 PointerTag RTag;      /*左右标识*/   
  }BiThrNode,*BiThrTree;  
  
  
  /*中序遍历进行中序线索化*/
  void  InThreading(BiThrNode p)
  {
    if(p)
    {
    	InThreading(p->lchild);/*递归左子树线索化*/
    	if(!p->lchild) /*没有左孩子*/ 
		{
			p->LTag=Thread;/*前驱线索*/
            p->lchild=pre; /*左孩子指针指向前驱*/
		 } 
		 if(!pre->rchlid){ /*前驱没有右孩子*/ 
		  pre->RTag=Thread; /*后继线索*/ 
		  pre->rchlid=p; /*前驱右孩子指针指向后继(当前节点p)*/ 
		 } 
		 pre=p; /*保持pre指向p的前驱*/ 
		 InThreading(p->rchlid);/*递归右子树线索化*/ 
	}
  	
  	
   } 
   
   
    /*T指向头结点 头结点左链lchild指向根节点 头结点右链rchild指向中序遍历的
	最后一个结点 中序遍历二叉线索链表表示的二叉树T*/
   Status InOrderTraverse_Thr(BiThrTree T)
   {
   	BiThrTree p;
   	p=T->lchild;/*p指向根结点*/ 
   	while(p!=T) /*空树或遍历结束时 p==T*/ 
   	{
   		while(p->LTag==Link)/*当LTag==0时循环到中序序列第一个结点*/ 
        p=p->lchild;
        printf("#%c",p->data);/*显示结点数据 可以更改为其他队结点操作*/ 
        while(p->RTag==Thread &&p->rchlid!=T)
        {
        	p=p->rchlid;
        	printf("%c",p->data);
		}
		p=p->rchlid;/*p进至其右子树根*/ 
	   }
	   return OK;
   }
   
   
 
    
  
  
  
  
