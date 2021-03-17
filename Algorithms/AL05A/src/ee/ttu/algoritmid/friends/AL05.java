package ee.ttu.algoritmid.friends;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
public class AL05 {
    public DirectedGraph graph = new DirectedGraph();

    private class DirectedGraph {
        private Map<String, List<String>> graph = new HashMap<>();

        /**
         * Add undirected edge to the graph.
         *
         * @param one   one element of the edge
         * @param other the other element of edge
         */
        public void addEdge(String one, String other) {
            if (!graph.containsKey(one)) {
                List<String> edges = new ArrayList<>();
                edges.add(other);
                graph.put(one, edges);
            } else {
                if (!graph.get(one).contains(other)) {
                    graph.get(one).add(other);
                }
            }
        }

        /**
         * Return the graph.
         *
         * @return the internal graph structure.
         */
        public Map<String, List<String>> getGraph() {
            return graph;
        }

        /**
         * Perform breadth first search.
         *
         * @param start the vertex to start the search from
         * @param goal  the goal vertex to find
         * @return the number of vertices of the path from start to goal including start and goal: e.g.,
         * start = A, goal = C, path = A, B, C => 3, so: (3, [A, B, C]), the path itself as a list of strings.
         * NB! You can return null as path and only return the number of nodes
         * that connect the start and goal vertices for partial credit
         * (some tests only check for number of nodes)
         */
        public SimpleEntry<Integer, List<String>> breadthFirstSearch(String start, String goal) {
            Queue<String> queue = new ArrayDeque<>();
            Set<String> alreadyVisited = new HashSet<>();
            Map<String, String> parents = new HashMap<>();
            boolean found = false;
            queue.add(start);
            String current;

            while (!queue.isEmpty() && !found) {
                current = queue.remove();

                if (graph.get(current) != null){
                    alreadyVisited.add(current);
                    for (String child: graph.get(current)) {
                        if (!alreadyVisited.contains(child)){
                            if (child.equals(goal)) {
                                parents.put(child, current);
                                found = true;
                                break;
                            }
                            queue.add(child);
                            parents.put(child, current);
                        }
                    }
                    queue.removeAll(alreadyVisited);
                }
            }

            List<String> anwser = new ArrayList<>();
            if (found) {
                String child = goal;
                while (!anwser.contains(start)) {
                    anwser.add(child);
                    child = parents.get(child);
                }
                Collections.reverse(anwser);
                return new SimpleEntry<>(anwser.size(), anwser);
            }
            return null;
        }
    }


    /**
     * Use buildGraphAndFindLink to build a graph using the DirectedGraph class and then use its breadthFirstSearch to
     * return the links.
     *
     * @param friends the list of friends as pairs
     *                (e.g., [["Juhan", "Jaan"], ["Juhan", "Siiri"]] means that "Juhan" knows "Jaan" and "Siiri")
     * @param pair    the pair to be searched
     * @return the number of people that connect the searched pair including the pair itself (e.g., if pair is
     * = ["Mark", "Johanna"] and path is ["Mark", "Peter", "Siiri", "Johanna"], the number of people is 4) the list of people that connect
     * the searched pair (e.g., pair = ["Mark", "Sam"] => result = ["Mark", "Siiri", "Helen", "Peeter", "Sam"])
     */
    public SimpleEntry<Integer, List<String>> buildGraphAndFindLink(List<SimpleEntry<String, String>> friends,
                                                                     SimpleEntry<String, String> pair) {
        for (SimpleEntry<String, String> entry: friends) {
            graph.addEdge(entry.getKey(), entry.getValue());
        }
        return graph.breadthFirstSearch(pair.getKey(), pair.getValue());
    }
}