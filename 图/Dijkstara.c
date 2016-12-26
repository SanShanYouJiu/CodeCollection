#include <stdio.h> //Dijkstara 算法 - 最短路径
#define OK 1
#define ERROR 0
#define TRUE 1
#define FALSE 0
#define MAXSIZE 100
#define MAXVEX 100  /*最大顶点数 应由用户定义*/
#define INFINITY 65535 /*用65535表示OO*/
typedef char VertexType; /*顶点类型应由用户定义*/
typedef int  EdgeType; /*边上的权值类型应由用户定义*/
typedef int QElemType;
#define    MAX 100
typedef int Patharc[MAXVEX];//用于存储最短路径下标的数组
typedef int ShortPathTable[MAXVEX];//用于存储到各点最短路径的权值和
typedef  int  Boolean;/*Boolean是布尔类型 其值是True或False*/
typedef int Status;
typedef struct {
	VertexType vexs[MAXVEX];/*顶点表*/
	EdgeType arc[MAXVEX][MAXVEX];/*临接矩阵 可看成表*/
	int numVertexes,numEdges;/*图中当前的顶点数和边数*/
} MGraph;

/*建立无向网图的邻接矩阵表示*/
void CreateMGraph(MGraph *G) {
	int i,j,k,w;
	printf("输入顶点数和边数:\n");
	scanf("%d,%d",&G->numVertexes,&G->numEdges);/*输入顶点数和边数*/
	for(i=0; i<G->numVertexes; i++)
		scanf(&G->vexs[i]);
	for(i=0; i<G->numVertexes; i++)
		for(j=0; j<G->numVertexes; j++)
			G->arc[i][j]=INFINITY;/*领接矩阵初始化*/

	for(k=0; k <G->numVertexes; k++) { /*读入numEdges条边 建立邻接矩阵*/
		printf("输入边(vi,vj)上的下标i,下标j和权w:\n");
		scanf("%d,%d,%d",&i,&j,&w);/*输入边(vi,vj)上的权w*/
		G->arc[i][j]=w;
		G->arc[j][i]=G->arc[i][j];/*因为是无向图 矩阵对称*/
	}


// P[v]的值为前驱顶点下标 D[v]表示v0到v的最短路径长度和
void ShortestPath_Dijikstar(MGraph G,int v0,Patharc *P,ShortPathTable *D){

 int v,w,k,min;
 int final[MAXVEX];//final[w]=1表示求得顶点v0至vw的最短路径
  for(v=0;v<G.numVertexes;v++){ //初始化数据
  	final[v]=0;          //全部顶点初始化为未知最短路径状态
  	(*D)[v]=G.arc[v0][v]; //将与v0点有连线的顶点加上权值
  	(*P)[v]=-1;        //初始化路径数组p为0
  }
     (*D)[v0]=0; //v0至v0路径为0
     final[v0]=1; //v0至v0不需要求路径

     //开始主循环 每次求得v0到某个顶点的最短路径
 for(v=0;v<G.numVertexes;v++)
 {
 	min=INFINITY; //当前所知离v0顶点的最近距离
 	for(w=0;w<G.numVertexes;w++){ //寻找离v0最近的顶点
 		if(!final[w] && (*D)[w]<min)
 		{
 			 k=w;
			 min=(*D)[w]; //w顶点离v0顶点更近
		 }
	 }
	 final[k]=1;//将目前找到的最近的顶点置为1
	 for(w=0;w<G.numVertexes;w++)//修正当前最短路径及距离
	 {
	 	//如果经过v顶点的路径比现在这条路径长度短的话
	 	if(!final[w] && (min+G.arc[k][w] <(*D)[w]))
	 	{
	 	// 说明找到了更短的路径 修改D[w]和P[w]
	 		(*D)[w]=min + G.arc[k][w];//修改当前路径长度
	 		(*P)[w]=k;
		 }
	 }
	 }

}
}
