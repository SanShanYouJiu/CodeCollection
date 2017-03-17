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

}




Boolean visited[MAX]; /*访问标识的数组*/

/*领接矩阵的深度优先递归算法*/
void DFS(MGraph G,int i) {
	int j;
	visited[i]=TRUE;
	printf("%c",G.vexs[i]); //打印顶点 也可以其他操作
	for(j=0; j<G.numVertexes; j++)
		if(G.arc[i][j] ==1 && !visited[j])
			DFS(G,j);         // 对未访问的领接顶点递归调用

}


/*领接矩阵的深度遍历操作*/
void DFSTraverse(MGraph G) {
	int i;
	for(i=0; i<G.numVertexes; i++)
		visited[i]=FALSE; //初始所有订单状态都是未访问过状态
	for(i=0; i<G.numVertexes; i++)
		if(!visited[i])//对未访问过的顶点调用DFS 若是连通图 只会执行一次
			DFS(G,i);

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
	if(Q->front==Q->rear)
		return TRUE;
	else
		return FALSE;
}

/*邻接矩阵的广度遍历算法*/
void  BFSTraverse(MGraph G) {
	int i,j;
	Queue Q;
	for(i=0; i<G.numVertexes; i++)
		visited[i]=FALSE;
	InitQueue(&Q);//初始化一辅助用的队列
	for(i=0; i<G.numVertexes; i++) { //对每一个节点做循环
		if(!visited[i]) { // 若是未访问过就处理
			visited[i]=TRUE; // 设置当前节点访问过
			printf("%c",G.vexs[i]); //打印节点  或者其他操作
			EnQueue(&Q,i); //将此节点入队列
			while(!QueueEmpty(Q)) { //若当前节点不为空
				DeQueue(&Q,&i); //将队中元素出队列 赋值给i
				for(j=0; j<G.numVertexes; j++) {
					//判断其他顶点若与当前顶点存在边且未访问过
					if(G.arc[i][j] ==1 && !visited[j]) {
						visited[j]=TRUE; //将找到的此顶点标记为已访问
						printf("%c",G.vexs[j]); //打印结点
						EnQueue(&Q,j);
					}
				}
			}
		}

	}

}
