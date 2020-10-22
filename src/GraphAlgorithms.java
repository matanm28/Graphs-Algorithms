import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Collections;


public class GraphAlgorithms implements IGraphAlgorithms {
    private IGraph graph;

    public GraphAlgorithms(IGraph g) {
        this.init(g);
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(IGraph g) {
        this.graph = g;
        initNodesTag(1);
    }

    private void initNodesTag(int state) {
        //1 = white, 2 = grey, 3 = black
        for (INodeData node : this.graph.getV()) {
            node.setTag(state);
        }
    }

    /**
     * Compute a deep copy of this graph.
     *
     * @return
     */
    @Override
    public IGraph copy() {
        IGraph graphCopy = new UndirectedGraph();
        for (INodeData v : this.graph.getV()) {
            graphCopy.addNode(v);
            for (INodeData u : this.graph.getV(v.getKey())) {
                graphCopy.addNode(u);
                graphCopy.connect(v.getKey(), u.getKey());
            }
        }
        return graphCopy;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        Set<UndirectedEdge> connectedVertexes = new HashSet<>();
        for (INodeData v : this.graph.getV()) {
            for (INodeData u : this.graph.getV()) {
                if (v.getKey() == u.getKey()) continue;
                UndirectedEdge edge = new UndirectedEdge(v.getKey(), u.getKey());
                if (connectedVertexes.contains(edge)) continue;
                if (this.shortestPathDist(v.getKey(), u.getKey()) == -1) return false;
                connectedVertexes.add(edge);
            }
        }
        return false;
    }

    /**
     * returns the length of the shortest path between src to dest
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public int shortestPathDist(int src, int dest) {
        int dist = this.shortestPath(src, dest).size();
        return (dist == 0 && src != dest) ? -1 : dist;

    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<INodeData> shortestPath(int src, int dest) {
        return this.bfs(src, dest);
    }

    private List<INodeData> dijkstra(int src, int dest) {
        return new ArrayList<>();
    }

    private List<INodeData> bfs(int src, int dest) {
        INodeData sourceNode = this.graph.getNode(src);
        Integer[] prev = new Integer[this.graph.nodeSize() + 1];
        if (sourceNode == null) {
            return new ArrayList<>();
        }
        this.initNodesTag(1);
        for (int i = 0; i < this.graph.nodeSize(); ++i) {
            prev[i] = null;
        }
        Queue<INodeData> q = new LinkedList<>();
        q.offer(sourceNode);
        sourceNode.setTag(1);
        //1 = white, 2 = grey, 3 = black
        while (!q.isEmpty()) {
            INodeData currNode = q.poll();
            currNode.setTag(2);
            if (currNode.getKey() == dest) {
                currNode.setTag(3);
                break;
            }
            for (INodeData neighbor : currNode.getNi()) {
                if (neighbor.getTag() == 1) {
                    q.offer(neighbor);
                    neighbor.setTag(2);
                    prev[neighbor.getKey()] = currNode.getKey();
                }
            }
            currNode.setTag(3);
        }
        return reconstructPath(src, dest, prev);
    }

    private @NotNull
    List<INodeData> reconstructPath(int src, int dest, Integer[] prev) {
        List<INodeData> path = new ArrayList<>();
        for (Integer at = dest; at != null; at = prev[at]) {
            path.add(this.graph.getNode(at));
        }
        Collections.reverse(path);
        if (path.get(0).getKey() != src) {
            path.clear();
        }
        return path;
    }
}
