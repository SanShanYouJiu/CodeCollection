
import java.util.Arrays;
import java.util.Scanner;

/**Dijkstara算法 --最短路径
 * Created by han on 2016/12/26.
 */
public class Dijkstara {

    public    final  int MAXVEX=100;
    public    final  int MAXSIZE=5;
    public    final  int MAX=100;
    public    final  int INFINITY=65535;

    public int[] Patharc = new int[MAXVEX];
    public int[] ShortPathTable = new int[MAXVEX];

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


    void CreateMGraph(MGraph G){

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


    void   ShortestPath_Dijkstar(MGraph G,int v0,int[] patharc,int[] shortPathTable){
        int v,w,k=0,min;

        boolean[] Namefinal = new boolean[MAXVEX];
        for (v=0;v<G.numVertexes;v++){
            Namefinal[v] =false;
            shortPathTable[v]=G.arc[0][v];
            patharc[v]=-1;
        }
        shortPathTable[v0]=0;
        Namefinal[v0]=true;

        for(v=0;v<G.numVertexes;v++){
            min=INFINITY;

            for (w = 0; w < G.numVertexes; w++) {
                if(!Namefinal[w] && shortPathTable[w]<min) {
                    k = w;
                    min = shortPathTable[w];
                }
            }
           Namefinal[k]=true;
            for (w=0;w<G.numVertexes;w++){
                if(!Namefinal[w]&& (min+G.arc[k][w])<shortPathTable[w]){
                    shortPathTable[w]=min+ G.arc[k][w];
                    patharc[w]=k;
                }
            }
        }

        for (v=0;v<G.numVertexes;v++){
            System.out.println("shortPathTable :"+shortPathTable[v]
            +"patharc :"+patharc[v]);
        }
    }

    public static void main(String[] args) {
        MGraph G = new Dijkstara().new MGraph();
        Dijkstara D = new Dijkstara();
        D.CreateMGraph(G);
        D.ShortestPath_Dijkstar(G,0,D.Patharc,D.ShortPathTable);
    }
}
