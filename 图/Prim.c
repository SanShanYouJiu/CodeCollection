#include <stdio.h>
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
typedef  int  Boolean;/*Boolean是布尔类型 其值是True或False*/
typedef int Status;
typedef struct {
	VertexType vexs[MAXVEX];/*顶点表*/
	EdgeType arc[MAXVEX][MAXVEX];/*临接矩阵 可看成表*/
	int numVertexes,numEdges;/*图中当前的顶点数和边数*/
} MGraph;


// prim/普里姆  算法 生成最小生成树
void MinniSpanTree_Prim(MGraph G)
{
	int  min,i,j,k;
	int adjvex[MAXVEX]; //保存相关顶点下标
	int lowcost[MAXVEX];//保存相关顶点间边的权值
	lowcost[0]=0;  //初始化第一个权值为0 即v0加入生成树
	 //lowcast的值为0 在这里就是此下标的顶点已经加入生成树
	adjvex[0]=0;// 初始化第一个顶点下标为0

	for(i=1;i<G.numVertexes;i++) //循环除下标为0外的全部顶点
	{
	  lowcost[i]=G.arc[0][i]; //将V0顶点与之有边的权值存入数组
	  adjvex[i]=0;  //初始化都为v0的下标
	}
	for(i=1;i<G.numVertexes;i++)
	{
		min=INFINITY; //初始化最小权值为oo
		                //通常设置为不可能的大数值 如32767 65535等
		j=1;k=0;
		while(j<G.numVertexes) //循环全部顶点
		{
			if(lowcost[j]!=0 && lowcost[j] <min)
            {//如果权值不为0且权值小于min
            	min=lowcost[j];//则让当前权值成最小值
            	k=j;//将当前最小值的下标存入k
			}
			j++;
		}
		printf("(%d,%d)",adjvex[k],k);//打印当前顶点边中权值最小边
		lowcost[k]=0;// 将当前顶点的权值设置为0 表示此顶点已经完成任务
		for(j=1;j<G.numVertexes;j++) //循环全部顶点
		{
			if(lowcost[j]!=0 && G.arc[k][j]<lowcost[j])
			{ //若下标为k顶点各边权值小于此前这些顶点未被加入生成树权值
				lowcost[j]=G.arc[k][j]; //将较小权值存入lowcost
				adjvex[j]=k;//将下标为k的顶点存入adjvex
			}
		}
	}
 }

  
