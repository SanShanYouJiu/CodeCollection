
import java.util.Scanner;

/**拓扑排序
 * Created by han on 2017/1/5.
 */
public class TopologicalSort {

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
                    ", firstedge=" + firstedge+
                    '}';
        }
    }


    class GraphAdjList {
        AdjList[] adjList = new AdjList[MAXVEX];
        int numVertexes, numEdges;
    }


    void CreateALGraph(GraphAdjList G) {
        int i, j, k;
        EdgeNode e;
        System.out.println("输入顶点数和边数");
        Scanner sc = new Scanner(System.in);
        G.numVertexes = sc.nextInt();
        G.numEdges = sc.nextInt();
        for (i = 0; i < G.numVertexes; i++) {
            G.adjList[i] = new AdjList();
            System.out.println("输入"+i+"节点数据");
            G.adjList[i].data = new Scanner(System.in).next();//在C语言版中 是采用的scanf函数进行输入顶点信息
            G.adjList[i].firstedge = null;
        }


        for (k = 0; k < G.numEdges; k++) {
            System.out.println("输入边(vi,vj)上的顶点序号");
            i = sc.nextInt();
            j = sc.nextInt();
            e = new EdgeNode();


            e.adjvex = j;
            e.next = G.adjList[i].firstedge;
            G.adjList[i].firstedge = e;
            G.adjList[j].in++;




//            e = new EdgeNode();

//            e.adjvex=i;
//            e.next=G.adjList[j].firstNode;
//            G.adjList[i].firstNode=e;
        }

    }



    //拓扑排序 若GL无回路 则输出拓扑排序序列并返回true，若有回路返回false
    boolean TopologicalSort(GraphAdjList GL) {
        EdgeNode e;
        int i, k, gettop;
        int top = 0;
        int count = 0;
        int[] stack = new int[GL.numVertexes];
        for (i = 0; i < GL.numVertexes; i++)
            if (GL.adjList[i].in == 0)
                stack[++top] = i;

        while (top != 0) {
            gettop = stack[top--];
            System.out.print(GL.adjList[gettop].data);//打印结点
            count++;
            for (e = GL.adjList[gettop].firstedge; e!=null;e = e.next) {
                    k = e.adjvex;
                if ((--GL.adjList[k].in)== 0 )
                    stack[++top] = k;
            }
        }

        if (count < GL.numVertexes)
            return false;
        else
            return true;

        }

    public static void main(String[] args) {
        TopologicalSort sort = new TopologicalSort();
        GraphAdjList GL = new TopologicalSort().new GraphAdjList();
        sort.CreateALGraph(GL);
        System.out.println(sort.TopologicalSort(GL));
    }

}
