#include <stdio.h>
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
typedef struct {
	int begin;
	int end;
	int weight;
} Edge;


typedef int Pathmatirx[MAXVEX][MAXVEX];
typedef int ShortPathTable[MAXVEX][MAXVEX];
/*Floyd算法 求网图G中各顶点v到其余顶点w最短路径p[v][w]及带权长度D[v][w]*/
void ShortestPath_Floyd(MGraph G,Pathmatirx *P,ShortPathTable *D) {
	int v,w,k;
	for(v=0; v<G.numVertexes; ++v) { /*初始化D与P*/
		for(w=0; w<G.numVertexes; ++w) {
			(*D)[v][w]=G.arc[v][w]; /*D[v][w]值即为对应点间的权值*/
			(*P)[v][w]=w;    /*初始化P*/
		}
	}

	for(k=0; k<G.numVertexes; ++k) {
		for(v=0; v<G.numVertexes; ++v) {
			for(w=0; w<G.numVertexes; ++w) {
				if((*D)[v][w]>(*D)[v][k]+(*D)[k][w]) {
					/*如果经过下标为k顶点路径比原俩点路径更短*/
					/*将当前俩点间权值设为更小的一个*/
					(*D)[v][w]=(*D)[v][k]+(*D)[k][w];
					(*P)[v][w]=(*P)[v][k]; /*路径设置经过下标为k的顶点*/
				}
			}
		}
	}

	//求最短路径的显示
	for(v=0; v<G.numVertexes; ++v) {
		for(w=v+1; w<G.numVertexes; w++) {
			printf("v%d-v%d weight: %d",v,w,(*D)[v][w]);
			k=(*P)[v][w];         //获得第一个路径顶点下标
			printf("path : %d",v); //打印源点
			while(k!=w) { //如果路径顶点下标不是终点
				printf("-> %d",k);//打印路径顶点
				k=(*P)[k][w]; //获得下一个路径顶点下标
			}
			printf("-> %d\n",w);//打印终点
		}
		printf("\n");
	}
}
