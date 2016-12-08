#include <stdio.h>
#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAXVEX 100  /*最大顶点数 应由用户定义*/ 
#define INFINITY 65535 /*用65535表示OO*/ 
typedef char VertexType; /*顶点类型应由用户定义*/
typedef int  EdgeType; /*边上的权值类型应由用户定义*/ 
#define    MAX 100 
typedef  int  Boolean;/*Boolean是布尔类型 其值是True或False*/
typedef struct 
{
	  VertexType vexs[MAXVEX];/*顶点表*/ 
	  EdgeType arc[MAXVEX][MAXVEX];/*临接矩阵 可看成表*/
	  int numVertexes,numEdges;/*图中当前的顶点数和边数*/	   
 }MGraph;
 
/*建立无向网图的邻接矩阵表示*/ 
void CreateMGraph(MGraph *G)
{
	int i,j,k,w;
	printf("输入顶点数和边数:\n");
	scanf("%d,%d",&G->numVertexes,&G->numEdges);/*输入顶点数和边数*/
	for(i=0;i<G->numVertexes;i++)
	scanf(&G->vexs[i]);
	for(i=0;i<G->numVertexes;i++)
	for(j=0;j<G->numVertexes;j++)
	 G->arc[i][j]=INFINITY;/*领接矩阵初始化*/
	 
	 for(k=0;k <G->numVertexes;k++)/*读入numEdges条边 建立邻接矩阵*/
	 {
	 	printf("输入边(vi,vj)上的下标i,下标j和权w:\n");
	    scanf("%d,%d,%d",&i,&j,&w);/*输入边(vi,vj)上的权w*/
		G->arc[i][j]=w;
		G->arc[j][i]=G->arc[i][j];/*因为是无向图 矩阵对称*/  
	  } 
	
} 
 
  
  
 
Boolean visited[MAX]; /*访问标识的数组*/
   
  /*领接矩阵的深度优先递归算法*/
	void DFS(MGraph G,int i){
	 int j;
	 visited[i]=TRUE;
	  printf("%c",G.vexs[i]); //打印顶点 也可以其他操作
	  for(j=0;j<G.numVertexes;j++)
	    if(G.arc[i][j] ==1 && !visited[j])
		 DFS(G,j);         // 对未访问的领接顶点递归调用
		  
	 } 
	 
	 
	 /*领接矩阵的深度遍历操作*/
	 void DFSTraverse(MGraph G)
	 {
	 	int i;
	 	for(i=0;i<G.numVertexes;i++)
	 	visited[i]=FALSE; //初始所有订单状态都是未访问过状态 
	 	for(i=0;i<G.numVertexes;i++)
	 	if(!visited[i])//对未访问过的顶点调用DFS 若是连通图 只会执行一次 
	 	DFS(G,i);

	  } 
	  
	   
	 
	   
	   
	  
	  
	  
	  
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 

 

