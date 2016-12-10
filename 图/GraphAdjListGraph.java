
import java.util.Scanner;

/** 无向图邻接表
 * Created by han on 2016/12/7.
 */
public class GraphAdjListGraph {


    public    final  int MAXVEX=100;
    public    final  int INFINITY=65535;
    public    final  int MAX=100;
    public    final  int MAXSIZE=5;
    // 邻接表


    class EdgeNode{/*边表结点*/
        int adjvex;
        int weight;
        EdgeNode next;
    }

    class  AdjList
    {
        AdjList data;
        EdgeNode firstNode;//边表头指针

        @Override
        public String toString() {
            return "AdjList{" +
                    "data=" + data +
                    ", firstNode=" + firstNode +
                    '}';
        }
    }


    class  GraphAdjList{
        AdjList[] adjList=new AdjList[MAXVEX];
        int numVertexes,numEdges;
    }



    void CreateALGraph(GraphAdjList G){
        int i,j,k;
        EdgeNode e;
        System.out.println("输入顶点数和边数");
        Scanner sc = new Scanner(System.in);
        G.numVertexes = sc.nextInt();
        G.numEdges=sc.nextInt();
        for (i = 0; i < G.numVertexes; i++) {
            G.adjList[i]=new AdjList();
            G.adjList[i].data = new AdjList();//在C语言版中 是采用的scanf函数进行输入顶点信息
            G.adjList[i].firstNode = null;
        }


        for (k=0;k<G.numEdges;k++){
            System.out.println("输入边(vi,vj)上的顶点序号");
            i = sc.nextInt();
            j = sc.nextInt();
            e = new EdgeNode();
            e.adjvex=j;
            e.next=G.adjList[i].firstNode;
            G.adjList[i].firstNode=e;

            e = new EdgeNode();

            e.adjvex=i;
            e.next=G.adjList[j].firstNode;
            G.adjList[i].firstNode=e;
        }

    }


    boolean[] visited = new boolean[MAX];


    void DFS(GraphAdjList GL,int i){
        EdgeNode p;
        visited[i]=true;
        System.out.println(GL.adjList[i].data);
        p=GL.adjList[i].firstNode;
        while (p != null) {
            if (!visited[p.adjvex])
                DFS(GL, p.adjvex);
            p = p.next;
        }
    }



    void DFSTraverse(GraphAdjList GL){

        int i;
        for (i=0;i<GL.numVertexes;i++)
            visited[i]=false;
        for (i=0;i<GL.numVertexes;i++)
            if (!visited[i])
                DFS(GL,i);//对未访问过的顶点调用DFS 若是连通图 只会执行一次
    }


    class  Queue
    {
        Object[] data = new Object[MAXSIZE];
        int front;
        int rear;
    }

    boolean InitQueue(Queue Q){
        Q.front=0;
        Q.rear=0;
        return  true;
    }

    boolean EnQueue(Queue Q,Object e){
        if((Q.rear+1)%MAXSIZE ==Q.front)
            return  false;
        Q.data[Q.rear]=e;
        Q.rear=(Q.rear+1)%MAXSIZE;

        return  true;
    }


    Object DeQueue(Queue Q){
        Object e;
        if(Q.front ==Q.rear)
            return  false;
        e=Q.data[Q.front];
        Q.front=(Q.front+1)%MAXSIZE;
        return  e;
    }


    boolean QueueEmpty(Queue Q){

        if(Q.front==Q.rear)
            return true;
        else
            return false;
    }


    void BFSTraverse(GraphAdjList GL ){

        int i;
        EdgeNode p;
        Queue Q=new Queue();
        for(i=0;i<GL.numVertexes;i++)
            visited[i]=false;
        InitQueue(Q);

        for (i=0;i<GL.numVertexes;i++)
        {
            if(!visited[i])
            {
                visited[i]=true;
                System.out.println(GL.adjList[i].data);
                EnQueue(Q,i);
                while (!QueueEmpty(Q)){
                    i = (int) DeQueue(Q);
                    p=GL.adjList[i].firstNode;
                    while (p!=null){
                        if(!visited[p.adjvex]){
                            visited[p.adjvex]=true;
                            System.out.println(GL.adjList[p.adjvex].data);
                            EnQueue(Q,p.adjvex);
                        }
                        p=p.next;
                    }

                }
            }
        }
    }





    public static void main(String[] args)   {
        GraphAdjListGraph graph = new GraphAdjListGraph();
        GraphAdjList graphAdjList = new GraphAdjListGraph().new GraphAdjList();
        graph.CreateALGraph(graphAdjList);

        graph.DFSTraverse(graphAdjList);
        graph.BFSTraverse(graphAdjList);

    }
}
