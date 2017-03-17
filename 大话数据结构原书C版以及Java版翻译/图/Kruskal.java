
import java.util.Arrays;
import java.util.Scanner;

/**Kruskal算法 内部随便用的冒泡排序 如果以后有机会再改进 -最小生成树
 * Created by han on 2016/12/24.
 */
public class Kruskal {


    public    final  int MAXVEX=100;
    public    final  int MAXSIZE=5;
    public    final  int MAXEDGE=20;
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

    class Edge{
        int begin;
        int end;
        int weigth;

        @Override
        public String toString() {
            return "Edge{" +
                    "begin=" + begin +
                    ", end=" + end +
                    ", weigth=" + weigth +
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
//            G.arc[j][i]=G.arc[i][j];
        }
    }


    void MiniSpanTree_Kruskal(MGraph G){
        int i,n,m;
        Edge[] edges = new Edge[MAXEDGE];
        int[] parent = new int[MAXEDGE];
        int min=INFINITY;
        int k=0,j;
        Edge middleEdge;

        for (j=0;j<G.numVertexes;j++){
            for (i=0;i<G.numVertexes;i++){
                if(G.arc[i][j] <min) {
                    edges[k] = new Edge();
                    edges[k].begin = i;
                    edges[k].end = j;
                    edges[k].weigth = G.arc[i][j];
                    k++;
                }
            }
        }

        for (i=0;i<k;i++){
            for (j=i+1;j<k;j++){
                if(edges[i].weigth>edges[j].weigth){
                    middleEdge=edges[i];
                    edges[i]=edges[j];
                    edges[j]=middleEdge;
                }
            }
        }

       for(i=0;i<G.numVertexes;i++)
           parent[i]=0;
        for (i=0;i<G.numVertexes;i++){
            n=Find(parent,edges[i].begin);
            m=Find(parent,edges[i].end);
            if(n!=m){
                parent[n]=m;
                System.out.println("("+edges[i].begin+","+edges[i].end+")"+edges[i].weigth);
            }
        }

        }


    int Find(int[] parent,int f){
        while (parent[f]>0)
            f=parent[f];
        return f;
    }

    public static void main(String[] args) {
        MGraph G = new Kruskal().new MGraph();
        Kruskal kruskal = new Kruskal();
        kruskal.CreateMGrapht(G);
        kruskal.MiniSpanTree_Kruskal(G);

    }

}
