import java.util.Arrays;
import java.util.Scanner;

/**
无向图邻接矩阵
 * Created by han on 2016/12/7.
 */
public class MGraphGraph {


    public    final  int MAXVEX=100;
    public    final  int MAX=100;
    public    final  int INFINITY=65535;

    //  邻接矩阵

    class MGraph{
        String[] vexs=new String[MAXVEX];
        int[][] arc = new int[MAXVEX][MAXVEX];
        int numVertexes,numEdges;

        @Override
        public String toString() {
            return "MGraph{" +
                    "vexs=" + Arrays.toString(vexs) +
                    ", arc=" + Arrays.toString(arc) +
                    ", numVertexes=" + numVertexes +
                    ", numEdges=" + numEdges +
                    '}';
        }
    }



    void CreateMGrapht(MGraph G){

        int i,j,k,w;

        System.out.println("输入顶点数和边数");
        Scanner sc = new Scanner(System.in);
        G.numVertexes = sc.nextInt();
        G.numEdges = sc.nextInt();
        System.out.println("输入顶点信息");
        for (i=0;i<G.numVertexes;i++)
        G.vexs[i] = sc.next();
        for (i=0;i<G.numVertexes;i++)
            for (j=0;j<G.numVertexes;j++)
                G.arc[i][j]=INFINITY;

        for (k=0;k<G.numEdges;k++){
            System.out.println("输入边(vi,vj)上的下标i,上标j和权w");
            i = sc.nextInt();
            j = sc.nextInt();
            w=sc.nextInt();
            G.arc[i][j]=w;
            G.arc[j][i]=G.arc[i][j];
            System.out.println(G.arc[i][j]);
        }
    }

    boolean[] visited = new boolean[MAX];


    void  DFS(MGraph G,int i)
     {
         int j;
         visited[i]=true;
         System.out.println(G.vexs[i]);
         for ( j = 0; j <G.numVertexes ; j++)
             if(G.arc[i][j]==1 && !visited[j])
                 DFS(G,j);
     }



    void  DFSTraverse(MGraph G){
        int i;
        for (i=0;i<G.numVertexes;i++)
            visited[i]=false;
        for (i=0;i<G.numVertexes;i++)
            if(!visited[i])//对未访问过的顶点调用DFS 若是连通图 只会执行一次
                DFS(G,i);
    }


    public static void main(String[] args)   {
        MGraphGraph graph = new MGraphGraph();
        MGraph mGraph = new MGraphGraph().new MGraph();
        graph.CreateMGrapht(mGraph);
        System.out.println(mGraph);

//        GraphAdjList graphAdjList = new Graph().new GraphAdjList();
//        graph.CreateALGraph(graphAdjList);
        graph.DFSTraverse(mGraph);

    }
}
