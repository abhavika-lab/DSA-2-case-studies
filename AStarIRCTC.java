import java.util.*;

class Edge {
    int to, cost;

    Edge(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}

class Node {
    int vertex;
    int g;
    int f;

    Node(int vertex, int g, int f) {
        this.vertex = vertex;
        this.g = g;
        this.f = f;
    }
}

public class AStarIRCTC {

    static final int MUM = 0;
    static final int NGP = 1;
    static final int HYD = 2;
    static final int PAT = 3;
    static final int RNC = 4;
    static final int BBS = 5;
    static final int VSK = 6;
    static final int KOL = 7;

    static String[] stations = {
            "MUM", "NGP", "HYD", "PAT",
            "RNC", "BBS", "VSK", "KOL"
    };

    static List<Edge>[] graph;

    static void addEdge(int u, int v, int w) {
        graph[u].add(new Edge(v, w));
    }

    public static void main(String[] args) {

        int n = 8;

        graph = new ArrayList[n];

        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        addEdge(MUM, NGP, 400);
        addEdge(MUM, HYD, 500);

        addEdge(NGP, PAT, 350);
        addEdge(NGP, RNC, 300);

        addEdge(HYD, BBS, 400);
        addEdge(HYD, VSK, 250);

        addEdge(PAT, KOL, 250);
        addEdge(RNC, KOL, 300);
        addEdge(BBS, KOL, 200);
        addEdge(VSK, KOL, 350);

        int[] heuristic = {
                900,
                600,
                750,
                400,
                350,
                300,
                550,
                0
        };

        aStar(MUM, KOL, heuristic);
    }

    static void aStar(int source, int target, int[] h) {

        int n = graph.length;

        int[] g = new int[n];
        int[] parent = new int[n];

        Arrays.fill(g, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        g[source] = 0;

        PriorityQueue<Node> pq =
                new PriorityQueue<>((a, b) -> a.f - b.f);

        pq.offer(new Node(source, 0, h[source]));

        while (!pq.isEmpty()) {

            Node current = pq.poll();

            int u = current.vertex;

            if (u == target)
                break;

            for (Edge e : graph[u]) {

                int newG = g[u] + e.cost;

                if (newG < g[e.to]) {

                    g[e.to] = newG;
                    parent[e.to] = u;

                    pq.offer(
                            new Node(
                                    e.to,
                                    newG,
                                    newG + h[e.to]
                            )
                    );
                }
            }
        }

        System.out.println("Shortest Fare : ₹" + g[target]);

        List<String> path = new ArrayList<>();

        for (int v = target; v != -1; v = parent[v])
            path.add(stations[v]);

        Collections.reverse(path);

        System.out.println("Optimal Path : "
                + String.join(" -> ", path));
    }
}
