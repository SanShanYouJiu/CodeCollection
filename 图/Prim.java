import java.util.Arrays;
import java.util.Scanner;

/**prim算法
 * Created by han on 2016/12/23.
 */
public class Prim {


    public    final  int MAXVEX=100;
    public    final  int MAXSIZE=5;
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


  void  MiniSpanTree_Prim(MGraph G){
      int min,i,j,k;
      int[] adjvex = new int[MAXVEX];
      int[] lowcost = new int[MAXVEX];

      lowcost[0]=0;
      adjvex[0]=0;
      for (i=1;i<G.numVertexes;i++) {
          lowcost[i] = G.arc[0][i];
          adjvex[i]=0;
      }

      for (i=1;i<G.numVertexes;i++) {
          min = INFINITY;
          j=1;k=0;
          while (j < G.numVertexes) {

              if(lowcost[j]!=0&&lowcost[j]<min) {
                  min = lowcost[j];
                  k=j;
              }
              j++;
          }
          System.out.println(adjvex[k]+""+k);
          lowcost[k]=0;
          for (j=1;j<G.numVertexes;j++) {
              if (lowcost[j] != 0 && G.arc[k][j] < lowcost[j]) {
                  lowcost[j] = G.arc[k][j];
                  adjvex[j]=k;
              }
          }
      }
  }


    public static void main(String[] args) {
        MGraph G = new Prim().new MGraph();
        Prim p = new Prim();
        p.CreateMGrapht(G);
        p.MiniSpanTree_Prim(G);
    }
}
