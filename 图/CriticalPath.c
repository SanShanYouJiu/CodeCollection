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
#define  MAX 100
typedef int QElemType;
typedef int Status;
typedef  int  Boolean;/*Boolean是布尔类型 其值是True或False*/
Boolean visited[MAX]; /*访问标识的数组*/


typedef struct EdgeNode { /*边表结点*/
	int adjvex; /*邻接点域 存储该顶点对应的下标*/
	EdgeType weight; /*用于存储权值 对于非网图可以不需要*/
	struct EdgeNode *next;/*链域 指向下一个邻结点*/
} EdgeNode;

typedef struct VertexNode { /*顶点表结点*/
	int in  ;        //顶点入度
	int data;/*顶点域 存储顶点信息*/
	EdgeNode *firstedge;/*边表头指针*/
} VertexNode,AdjList[MAXVEX];



typedef struct {
	AdjList adjlist;
	int numVertexes,numEdges;/*图中当前顶点数和边数*/
} GraphAdjList;

/*建立图的邻接表结构*/
void CreateALGraph(GraphAdjList *G) {
	int i,j,k;
	EdgeNode *e;
	printf("输入顶点数和边数:\n");
	scanf("%d,%d",&G->numVertexes,&G->numEdges);/*输入顶点数和边数*/
	for(i=0; i<G->numVertexes; i++) {
		scanf("%d",&G->adjlist[i].data);/*输入顶点信息*/
		G->adjlist[i].firstedge=NULL;/*将边表置为空表*/
	}
	for(k=0; k<G->numEdges; k++) { /*建立边表*/
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

int *etv,*ltv; //事件最早发生事件和最迟发生时间数组
int *stack2; // 用于存储拓扑序列的栈
int top2; //用于stack2的指针
//拓扑排序  用于关键路径计算
Status TopologicalSort(GraphAdjList *GL) {
	EdgeNode *e;
	int i,k,gettop;
	int top=0; //用于栈指针下标
	int count=0; //用于统计输出顶点的个数
	int *stack; //建栈存储入度为0的顶点
	stack=(int *)malloc(GL->numVertexes * sizeof( int ));
	for(i=0; i<GL->numVertexes; i++)
		if(GL->adjlist[i].in ==0) //将入度为0的顶点入栈
			stack[++top]=i;
	top2=0;	 //初始化为0
	etv=(int *)malloc(GL->numVertexes*sizeof(int)); //事件最早发生时间
	for(i=0; i<GL->numVertexes; i++)
		etv[i]=0; //初始化为0
	stack2=(int *)malloc(GL->numVertexes*sizeof(int));//初始化
	while(top!=0) {
		gettop=stack[top--]; //出栈
		printf("%d ->",GL->adjlist[gettop].data);//打印此节点
		count++;//统计输出顶点数
		stack2[++top2]=gettop;//将弹出的顶点序号压入拓扑序列的栈
		for(e=GL->adjlist[gettop].firstedge; e; e=e->next) {
			//对此顶点弧度表遍历
			k=e->adjvex;
			if(!(--GL->adjlist[k].in)) //将k号顶点领接点的入度减一
				stack[++top]=k; //若为0则入栈 以便下次循环输出
			if((etv[gettop]+e->weight)>etv[k])//求各顶点事件最早发生时间值
				etv[k]=etv[gettop]+e->weight;
		}
	}

	if(count <GL->numVertexes)//如果count小于顶点数 说明存在环
		return ERROR;
	else
		return OK;

}

//求关键路径 GL为有向网 输出GL的各项关键活动
void CriticalPath(GraphAdjList *GL) {
	EdgeNode *e;
	int i,gettop,k,j;
	int ete,lte; //声明活动最早发生时间和最迟发生时间变量
	TopologicalSort(GL);//求拓扑序列 计算数组etv和stack2的值
	ltv=(int *)malloc(GL->numVertexes*sizeof(int));//事件最晚发生时间
	for(i=0; i<GL->numVertexes; i++)
		ltv[i]=etv[GL->numVertexes-1]; //初始化ltv
	while(top2!=0) { //计算ltv
		gettop=stack2[top2--];//将拓扑序列出栈 后进后出
		for(e=GL->adjlist[gettop].firstedge; e; e=e->next) {
			//求各顶点事件的最迟发生时间ltv值
			k=e->adjvex;
			if(ltv[k]-e->weight<ltv[gettop]) //求各顶点事件最晚发生时间ltv
				ltv[gettop]=ltv[k]-e->weight;
		}
	}
	for(j=0; j<GL->numVertexes; j++) { //求ete，lte和关键活动
		for(e=GL->adjlist[j].firstedge; e; e=e->next) {
			k=e->adjvex;
			ete=etv[j];//活动最早发生时间
			lte=ltv[k]-e->weight;//活动最迟发生时间
			if(ete==lte)     //倆者相等即在关键路径上
				printf("<v%d,v%d> length:%d, ",
				       GL->adjlist[j].data,GL->adjlist[k].data,e->weight);
		}
	}

}
