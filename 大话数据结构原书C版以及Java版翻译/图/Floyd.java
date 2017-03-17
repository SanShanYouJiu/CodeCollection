import java.util.Arrays;
import java.util.Scanner;

/** Floyd算法 --最短路径算法
 * Created by han on 2017/1/2.
 */
public class Floyd {


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

     void  ShortestPath_Floyd(MGraph G,int[][] pathmatirx,int[][] ShortPathTable){
         int v,w,k;
         for (v=0;v<G.numVertexes;++v){
           for (w=0;w<G.numVertexes;++w) {
               ShortPathTable[v][w] = G.arc[v][w];
               pathmatirx[v][w]=w;
           }
         }

         for (k=0;k<G.numVertexes;++k){
             for (v=0;v<G.numVertexes;++v){
                 for (w=0;w<G.numVertexes;++w){
                     if(ShortPathTable[v][w] >ShortPathTable[v][k]+ShortPathTable[k][w]){
                          ShortPathTable[v][w]=ShortPathTable[v][k]+ShortPathTable[k][w];
                          pathmatirx[v][w]=pathmatirx[v][k];
                     }
                 }
             }
         }


         //求最短路径的显示
         for (v=0;v<G.numVertexes;++v){
             for (w=v+1;w<G.numVertexes;w++) {
                 System.out.println();
                 System.out.print("V:" + v + " V:" + w + " Weight[" + v + "]" + "[" + w + "]：" + ShortPathTable[v][w]);
                 k=pathmatirx[v][w];
                 System.out.print(" path:"+v);
                 while (k!=w){
                     System.out.print("->"+k);
                     k=pathmatirx[k][w];
                 }
                 System.out.print("->"+w);
             }
             System.out.print("\n");
         }

     }

    public static void main(String[] args) {
        Floyd floyd = new Floyd();
        MGraph G = new Floyd().new MGraph();
        floyd.CreateMGrapht(G);
        floyd.ShortestPath_Floyd(G, new int[floyd.MAXVEX][floyd.MAXVEX], new int[floyd.MAXVEX][floyd.MAXVEX]);
    }
}
