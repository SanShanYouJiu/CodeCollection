#include <stdio.h>
#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAXVEX 100  /*最大顶点数 应由用户定义*/ 
#define INFINITY 65535 /*用65535表示OO*/ 
typedef char VertexType; /*顶点类型应由用户定义*/
typedef int  EdgeType; /*边上的权值类型应由用户定义*/ 
#define  MAX 100 
typedef  int  Boolean;/*Boolean是布尔类型 其值是True或False*/
Boolean visited[MAX]; /*访问标识的数组*/


 typedef struct EdgeNode /*边表结点*/
{ 
      int adjvex; /*邻接点域 存储该顶点对应的下标*/
       EdgeType weight; /*用于存储权值 对于非网图可以不需要*/ 
       struct EdgeNode *next;/*链域 指向下一个邻结点*/ 
}EdgeNode;

 typedef struct VertexNode /*顶点表结点*/
 {
 	VertexType data; /*顶点域 存储顶点信息*/
 	EdgeNode *firstedge;/*边表头指针*/
 }VertexNode,AdjList[MAXVEX];
 
 
 
 typedef struct
 {
 	AdjList adjlist;
 	int numVertexes,numEdges;/*图中当前顶点数和边数*/
  }GraphAdjList;
  
  /*建立图的邻接表结构*/
 void CreateALGraph(GraphAdjList *G)
 {
 	int i,j,k;
 	EdgeNode *e;
 	printf("输入顶点数和边数:\n");
 	scanf("%d,%d",&G->numVertexes,&G->numEdges);/*输入顶点数和边数*/
 	for(i=0;i<G->numVertexes;i++)
	 {
	 	scanf(&G->adjlist[i].data);/*输入顶点信息*/ 
	 	G->adjlist[i].firstedge=NULL;/*将边表置为空表*/ 
	  } 
	  for(k=0;k<G->numEdges;k++)/*建立边表*/ 
	  {
	  printf("输入边(vi,vj)上的顶点序号:\n");
	  scanf("%d,%d",&i,&j);/*输入边(vi,vj)上的顶点序号*/
	  e=(EdgeNode *)malloc(sizeof(EdgeNode));/*向内存申请空间*/
	                                          /*生成边表结点*/
      e->adjvex=j;/*邻接序号j*/
	  e->next=G->adjlist[i].firstedge; /*将e指针指向当前顶点指向的节点*/
	  G->adjlist[i].firstedge=e;/*将当前顶点的指针指向e*/
	  e=(EdgeNode *)malloc(sizeof(EdgeNode));/*向内存申请空间*/		
	                                          /*生成边表结点*/
      e->adjvex=i;/*邻接序号为i*/
	  e->next=G->adjlist[j].firstedge;/*将e指针指向当前顶点指向的节点*/
	  G->adjlist[j].firstedge=e;/*将当前顶点的指针指向e*/							   	
	  }
  } 
  
  
     //邻接表的深度优先算法 
	  void  DFS(GraphAdjList *GL,int i)
	  {
	  	EdgeNode *p;
	  	visited[i]=TRUE;
	  	printf("%c",GL->adjlist[i].data); //打印结点 也可以进行其他操作 
	  	p=GL->adjlist[i].firstedge;
	  	while(p)
	  	{
	  		if(!visited[p->adjvex])
             DFS(GL,p->adjvex); //对为访问的邻接顶点递归调用 
             p=p->next;
		  }
	   } 
	   
	   
	   
	   
	   /*邻接表的深度遍历操作*/
	   void DFSTraverse(GraphAdjList GL)
	   {
	   	int i;
	   	for(i=0;i<GL->numVertexes;i++)
	   	 visited[i]=FALSE;
	   	 for(i=0;i<GL->numVertexes;i++)
	   	 if(!visited[i]) // 对未访问过的结点调用DFS 若是联通图 只会执行一次
		  DFS(GL,i); 
		
		} 
		
		
		
		
		
		
		
		
