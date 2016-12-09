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
	VertexType data; /*顶点域 存储顶点信息*/
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
		scanf(&G->adjlist[i].data);/*输入顶点信息*/
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


//邻接表的深度优先算法
void  DFS(GraphAdjList *GL,int i) {
	EdgeNode *p;
	visited[i]=TRUE;
	printf("%c",GL->adjlist[i].data); //打印结点 也可以进行其他操作
	p=GL->adjlist[i].firstedge;
	while(p) {
		if(!visited[p->adjvex])
			DFS(GL,p->adjvex); //对为访问的邻接顶点递归调用
		p=p->next;
	}
}




/*邻接表的深度遍历操作*/
void DFSTraverse(GraphAdjList *GL) {
	int i;
	for(i=0; i<GL->numVertexes; i++)
		visited[i]=FALSE;
	for(i=0; i<GL->numVertexes; i++)
		if(!visited[i]) // 对未访问过的结点调用DFS 若是联通图 只会执行一次
			DFS(GL,i);

}


typedef struct {
	QElemType data[MAXSIZE];
	int front;/*头指针*/
	int rear;/*尾指针 若队列不空 指向队列尾元素的下一个位置*/
} Queue;


/*初始化一个空队列Q*/
Status InitQueue(Queue *Q) {
	Q->front=0;
	Q->rear=0;
	return OK;
}



/*若队列未满 则插入元素e为Q的队尾元素*/
Status EnQueue (Queue *Q,QElemType e) {
	if((Q->rear+1) %MAXSIZE==Q->front)/*队列满的判断*/
		return ERROR;
	Q->data[Q->rear] =e;/*将元素e赋值给队尾*/
	Q->rear=(Q->rear+1)%MAXSIZE;/*rear指针向后移一位置*/
	/*若到最后则转到数组头部*/
	return OK;
}



/*若队列不空 则删除Q中队头元素 则e返回其值*/
Status DeQueue(Queue *Q,QElemType *e) {
	if(Q->front == Q->rear)/*队列空的判断*/
		return ERROR;
	*e=Q->data[Q->front];/*将队头元素赋值给e*/
	Q->front=(Q->front+1)%MAXSIZE;/*front指针向后移一位置*/
	/*若到最后则转到数组头部*/
	return OK;
}



Status QueueEmpty(Queue Q) {
	if(Q.front==Q.rear)
		return TRUE;
	else
		return FALSE;
}

//邻接表的广度遍历算法
void BFSTraverse(GraphAdjList *GL) {
	int i;
	EdgeNode *p;
	Queue Q;
	for(i=0; i<GL->numVertexes; i++)
		visited[i]=FALSE;
	InitQueue(&Q);
	for(i=0; i<GL->numVertexes; i++) {
		if(!visited[i]) {
			visited[i]=TRUE;
			printf("%c",GL->adjlist[i].data);//打印节点 也可以其他操作
			EnQueue(&Q,i);
			while(!QueueEmpty(Q)) {
				DeQueue(&Q,&i);
				p=GL->adjlist[i].firstedge;//找到当前顶点边表链表头指针
				while(p) {
					if(!visited[p->adjvex]) { //若此顶点未被访问
						visited[p->adjvex]=TRUE;
						printf("%c",GL->adjlist[p->adjvex].data);
						EnQueue(&Q,p->adjvex);  //将此顶点入队列
					}
					p=p->next;//指针指向下一个邻接点
				}
			}
		}
	}
}
