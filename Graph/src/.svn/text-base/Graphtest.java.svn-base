import java.util.ArrayList;
import java.util.Collections;

class Graphtest {
    
    public static final int GRAPH_SIZE = 15;
    public static final double EDGE_PERCENT = 0.3;

    public static void DFS(Graph G, int Parent[], int startVertex, boolean Visited[]) 
    {
        Visited[startVertex] = true;
        Edge tmp = G.edges[startVertex];
        while (tmp != null) {
            if (Visited[tmp.neighbor] == false) {
                Parent[tmp.neighbor] = startVertex;
                DFS(G, Parent, tmp.neighbor, Visited);
            }
            tmp = tmp.next;
        }   
    }

    public static void PrintPath(int Parent[], int endvertex) 
    {
        if (Parent[endvertex] == -1) {
            System.out.println(endvertex);
        }
        else {
            int tmp = endvertex;
            ArrayList<Integer> l = new ArrayList();
            l.add(tmp);
            while (Parent[tmp] != 0) {
                l.add(Parent[tmp]);
                tmp = Parent[tmp];
            }
            l.add(0);
            Collections.reverse(l);
            for (int elem : l) {
                System.out.print(elem + " ");               
            }
            System.out.println();
        }
    }

    public static void BFS(Graph G, int Parent[], int startVertex, boolean Visited[]) 
    {
        ArrayQueue queue = new ArrayQueue();
        Edge tmp;
        int vertex;
        Visited[startVertex] = true;
        queue.enqueue(new Integer(startVertex));
        while (!queue.empty()) {
            vertex = ((Integer)queue.dequeue()).intValue();
            for (tmp = G.edges[vertex]; tmp != null; tmp = tmp.next) {
                if (Visited[tmp.neighbor] == false) {
                    Visited[tmp.neighbor] = true;
                    Parent[tmp.neighbor] = vertex;
                    queue.enqueue(new Integer(tmp.neighbor));
                }
            }   
        }
    }

    public static void main(String args[]) 
    {
        boolean Visited[] = new boolean[GRAPH_SIZE];
        int Parent[] = new int[GRAPH_SIZE];
        Graph G = new Graph(GRAPH_SIZE);
        int i;
        for (i=0; i<G.numVertex;i++) 
        {
            Visited[i] = false;
            Parent[i] = -1;
        }
        G.randomize(EDGE_PERCENT);
        G.print();
        BFS(G,Parent,0,Visited);
//      System.out.print("Parent ");
//      for (int elem : Parent) {
//          System.out.print(elem + " "); 
//      }  
//      System.out.println();
        System.out.println("----------------");
        System.out.println("BFS:");
        System.out.println("----------------");
        for (i=0; i<G.numVertex;i++) 
        {
            System.out.println("Path from 0 to " + i + ":");
            PrintPath(Parent,i);
        }
        for (i=0; i<G.numVertex;i++) 
        {
            Visited[i] = false;
            Parent[i] = -1;
        }
        DFS(G,Parent,0,Visited);
//      System.out.print("Parent ");
//      for (int elem : Parent) {
//          System.out.print(elem + " "); 
//      }  
//      System.out.println();
        System.out.println("----------------");
        System.out.println("DFS:");
        System.out.println("----------------");
        for (i=0; i<G.numVertex;i++) 
        {
        System.out.println("Path from 0 to " + i + ":");
        PrintPath(Parent,i);
        }
    }
}