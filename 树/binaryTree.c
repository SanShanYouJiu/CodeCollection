#include <stdio.h>/*二叉树*/
#define OK 1
#define OVERFLOW 0
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAX_TREE_SIZE 100 /*存储空间初始化分配量*/
typedef int TElemType;/*树结点的数据类型 目前暂定为整形*/
  
  /*二叉树的二叉链表结点结构定义*/
typedef struct BiTNode /*结点定义*/
{
 TElemType data; /*结点数据*/ 
 struct BiTNode *lchild, *rchild;/*左右孩子指针*/ 
}BiTNode,*BiTree; 
  
  
 /*二叉树的前序遍历算法*/
  void  PreOrderTraverse(BiTree T)
  {
  	if(T==NULL)
  		retrun;
	   
	   printf("%c",T->data);
	   PreOrderTraverse(T->lchild);
	   PreOrderTraverse(T->rchild);
   } 
  
  /*二叉树的中序遍历递归算法*/
  void  InOrderTraverse(BiTree T)
  {
  	if(T=NULL)
  	return;
  	InOrderTraverse(T->lchild);
  	printf("%c",T->data);
  	InOrderTraverse(T->rchild);
  	
   } 
   
  
  /*二叉树的后续遍历递归算法*/
  void  PostOrderTraverse(BiTree T)
  {
  	if(T==NULL)
  	return;
  	PostOrderTraverse(T->lchild);
  	PostOrderTraverse(T->rchild);
  	print("%c",T->data);
   } 
  
   /*按照前序输入二叉树结点的值（一个字符）*/
   /*#表示空树 构造二叉树链表示空二叉树T*/ 
   void CreateBiTree(BiTree *T)
   {
    TElemType ch;
	scanf("%c",&ch);
	if(ch=='#')
	  *T=NULL;
	else{
		*T=(BiTree)malloc(sizeof(BiTree));
		if(!*T)
	    	exit(OVERFLOW);
		(*T)->data=ch;
		CreateBiTree(&(*T)->lchild);
		CreateBiTree(&(*T)->rchild);
				
	} 

	
		
   } 
    
  
  
  
  
