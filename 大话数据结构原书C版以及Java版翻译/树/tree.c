#include <stdio.h>/*树的双亲表示法结点结构定义*/
#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAX_TREE_SIZE 100 /*存储空间初始化分配量*/
typedef int TElemType;/*树结点的数据类型 目前暂定为整形*/
 /*结点结构*/ 
typedef struct PTNode{
	TElemType data;/*结点数据*/
	int parent;/*双亲位置*/ 
}PTNode; 
 
 
 typedef struct /*树结构*/ 
  {
 	PTNode nodes[MAX_TREE_SIZE];/*结点数组*/
 	int r,n;/*根的位置和节点数*/ 
 }PTree;
 


/*树的孩子表示法*/
typedef struct CTNode/*孩子结点*/
{
	int child;
	struct CTNode *next;  
 }*ChildPtr;
 
 typedef struct /*表头结构*/ 
 {
 	TElemType data;
 	ChildPtr firstchild;
  } CTBox;
  
  
  typedef struct /*树结构*/
  {
  	CTBox nodes[MAX_TREE_SIZE];
  	int r,n;	
  }CTree;
  
  
  /*树的孩子兄弟表示法结构定义*/ 
  typedef struct CSNode
  {
   TElemType data;
   struct CSNode *firstchild,*rightsib; 
   }CSNode,*CSTree;
  
  
  
  
  
  
  
  
  
