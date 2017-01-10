
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by han on 2017/1/8.
 */
public class CriticalPath {

    public final int MAXVEX = 100;

    public final int INFINITY = 65535;
    public final int MAX = 100;
    public final int MAXSIZE = 5;
    // 邻接表



    class EdgeNode {/*边表结点*/
        int adjvex;
        int weight;
        EdgeNode next;
    }


    class AdjList {
        int in;
        String data;
        EdgeNode firstedge;//边表头指针

        @Override
        public String toString() {
            return "AdjList{" +
                    "in=" + in +
                    ", data=" + data +
                    ", firstedge=" + firstedge +
                    '}';
        }
    }


    class GraphAdjList {
        AdjList[] adjList = new AdjList[MAXVEX];
        int numVertexes, numEdges;

        @Override
        public String toString() {
            return "GraphAdjList{" +
                    "adjList=" + Arrays.toString(adjList) +
                    ", numVertexes=" + numVertexes +
                    ", numEdges=" + numEdges +
                    '}';
        }
    }


    void CreateALGraph(GraphAdjList G) {
        int i, j, k,weight;
        EdgeNode e;
        System.out.println("输入顶点数和边数");
        Scanner sc = new Scanner(System.in);
        G.numVertexes = sc.nextInt();
        G.numEdges = sc.nextInt();
        for (i = 0; i < G.numVertexes; i++) {
            G.adjList[i] = new AdjList();
            System.out.println("输入" + i + "节点数据");
            G.adjList[i].data = new Scanner(System.in).next();
            G.adjList[i].firstedge = null;
        }


        for (k = 0; k < G.numEdges; k++) {
            System.out.println("输入边(vi,vj)上的顶点序号 以及权值");
            i = sc.nextInt();
            j = sc.nextInt();
            weight = sc.nextInt();
            e = new EdgeNode();


            e.adjvex = j;
            e.weight = weight;
            e.next = G.adjList[i].firstedge;
            G.adjList[i].firstedge = e;
            G.adjList[j].in++;



//            e = new EdgeNode();

//            e.adjvex=i;
//            e.next=G.adjList[j].firstNode;
//            G.adjList[i].firstNode=e;
        }

    }


    int[] etv = new int[MAXVEX];
    int[] ltv = new int[MAXVEX];
    int[] stack2 = new int[MAXVEX];
    int top2;


    //拓扑排序  用于构建关键路径计算
    boolean TopologicalSort(GraphAdjList GL) {
        EdgeNode e;
        int i, k, gettop;
        int top = 0;
        int count = 0;
        int[] stack = new int[GL.numVertexes];
        for (i = 0; i < GL.numVertexes; i++)
            if (GL.adjList[i].in == 0)
                stack[++top] = i;
       top2=0;
       for (i=0;i<GL.numVertexes;i++) //初始化etv
           etv[i]=0;

        while (top != 0) {
            gettop = stack[top--];
            count++;
            stack2[++top2] = gettop;
            for (e = GL.adjList[gettop].firstedge; e != null; e = e.next) {
                k = e.adjvex;
                if ((--GL.adjList[k].in) == 0)
                    stack[++top] = k;
                if ((etv[gettop]+e.weight)>etv[k])
                    etv[k] = etv[gettop]+e.weight;
            }
        }
        if (count < GL.numVertexes)
            return false;
        else
            return true;

    }

    /**
     * 关键路径算法
     * @param   GL
     */
    void CriticalPath(GraphAdjList GL){
        EdgeNode e;
        int i,gettop,k,j;
        int ete,lte;
        TopologicalSort(GL);
        for (i=0;i<GL.numVertexes;i++) //初始化ltv
            ltv[i]=etv[GL.numVertexes-1];
        while (top2!=0){ //计算ltv
            gettop=stack2[top2--];
            for (e=GL.adjList[gettop].firstedge;e!=null;e=e.next){
                k=e.adjvex;
                if(ltv[k]-e.weight<ltv[gettop]) {
                    ltv[gettop]=ltv[k]-e.weight;
                }
            }
        }
        //遍历每个顶点 查看ete是否等于lte 求关键活动
        for (j=0;j<GL.numVertexes;j++){
            for (e=GL.adjList[j].firstedge;e!=null;e=e.next){
                k=e.adjvex;
                ete=etv[j];
                lte=ltv[k]-e.weight;
                if (ete==lte)
                    System.out.println("<v" + GL.adjList[j].data
                            + ",v" + GL.adjList[k].data + "> length :" + e.weight);
            }
        }
    }


    public static void main(String[] args) {
        GraphAdjList graphAdjList = new CriticalPath().new GraphAdjList();
        CriticalPath criticalPath = new CriticalPath();
        criticalPath.CreateALGraph(graphAdjList);
        criticalPath.CriticalPath(graphAdjList);
    }

}
