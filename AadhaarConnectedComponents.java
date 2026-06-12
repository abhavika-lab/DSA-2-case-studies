import java.util.*;

public class AadhaarConnectedComponents {

    static ArrayList<Integer>[] graph;
    static boolean[] visited;
    static ArrayList<Integer> component;

    // DFS Function
    static void dfs(int node) {
        visited[node] = true;
        component.add(node);

        for (int neighbour : graph[node]) {
            if (!visited[neighbour]) {
                dfs(neighbour);
            }
        }
    }

    public static void main(String[] args) {

        int centres = 9;

        graph = new ArrayList[centres + 1];

        for (int i = 1; i <= centres; i++) {
            graph[i] = new ArrayList<>();
        }

        // c1 ↔ c2
        graph[1].add(2);
        graph[2].add(1);

        // c2 ↔ c3
        graph[2].add(3);
        graph[3].add(2);

        // c3 ↔ c1
        graph[3].add(1);
        graph[1].add(3);

        // c4 ↔ c5
        graph[4].add(5);
        graph[5].add(4);

        // c6 ↔ c7
        graph[6].add(7);
        graph[7].add(6);

        // c7 ↔ c8
        graph[7].add(8);
        graph[8].add(7);

        visited = new boolean[centres + 1];

        int componentCount = 0;

        System.out.println("DFS Trace:");

        for (int i = 1; i <= centres; i++) {

            if (!visited[i]) {

                componentCount++;

                component = new ArrayList<>();

                dfs(i);

                System.out.println("Component " +
                                   componentCount +
                                   ": " + component);
            }
        }

        System.out.println("\nTotal Connected Components = "
                           + componentCount);
    }
}
