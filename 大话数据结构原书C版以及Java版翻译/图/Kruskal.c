#include <stdio.h> //Kruskal算法 -最小生成树
#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAXSIZE 100
#define MAXEDGE 20
#define MAXVEX 100  /*最大顶点数 应由用户定义*/
#define INFINITY 65535 /*用65535表示OO*/
typedef char VertexType; /*顶点类型应由用户定义*/
typedef int  EdgeType; /*边上的权值类型应由用户定义*/
typedef int QElemType;
#define    MAX 100
typedef  int  Boolean;/*Boolean是布尔类型 其值是True或False*/
typedef int Status;
typedef struct {
	VertexType vexs[MAXVEX];/*顶点表*/
	EdgeType arc[MAXVEX][MAXVEX];/*临接矩阵 可看成表*/
	int numVertexes,numEdges;/*图中当前的顶点数和边数*/
} MGraph;


 /*对边集数组*/
typedef struct
{
	int begin;
	int end;
	int weight;
}Edge;

/*Kruskal算法生成最小生成树*/
void MiniSpanTree_Kruskal(MGraph G)//生成最小生成树
{
	int i,n,m;
	Edge edges[MAXEDGE];//定义边集数组
	int parent[MAXEDGE];//定义一数组用来判断边与边是否形成环路
	//此处省略将邻接矩阵G转换为边集数组edges并按权从小到大排序的代码
	int min=INFINITY;
	int k,j;
	Edges middleEdge;

	for(j=0;j<G.numVertexes;j++){
    	for(i=0;i<G.numVertexes;i++){
	        if(G.arc[i][j] <min){
	            edges[k].begin=i;
	            edges[k].end=j;
	            edges[k].weight=G.arc[i][j];
				k++;
			}
	   }
}
//冒泡排序
  for(i=0;i<k;i++){
    	for(j=i+1;j<k;j++){
	       if(edges[i].weight>edges[j].weight){
	       	  middleEdge=edges[i];
	       	  edges[i]=edges[j];
	       	  edges[j]=middleEdge;
		   }
		}

  }

	for(i=0;i<G.numEdges;i++)
	  parent[i]=0;//初始化数组值为0
	 for(i=0;i<G.numEdges;i++)//循环每一条边
	 {
	 	n=Find(parent,edges[i].begin);
	 	m=Find(parent,edges[i].end);
	 	if(n!=m)//假如n与m不等 说明此边没有与现有生成树形成环路
		 {
		 	parent[n]=m;//将此边的结尾顶点放入下标为顶点的parent中
			 //表示此顶点已经在生成树集合中
		 	printf("(%d,%d) %d",edges[i].begin
			 ,edges[i].end,edges[i].weight);
		  }
	  }
 }

 void Fine(int *parent,int f)//查找连线结点的尾部下标
 {
 	while(parent[f] >0)
 	f=parent[f];
 	return f;
 }
