import java.util.*;

public class UndirectedGraph implements IGraph {
    private Map<Integer, INodeData> vertexMap = new HashMap<>();
    private Set<INodeData> vertexSet = new HashSet<>();
    private Set<UndirectedEdge> edges = new HashSet<>();
    private String toString = null;
    private int modeCount = 0;

    public UndirectedGraph() {
    }

    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public INodeData getNode(int key) {
        return this.vertexMap.get(key);
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        return this.edges.contains(new UndirectedEdge(node1, node2));
    }

    /**
     * add a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(INodeData n) {
        if (this.vertexSet.contains(n)) {
            return;
        }
        this.modeCount++;
        this.vertexSet.add(n);
        this.vertexMap.put(n.getKey(), n);
    }

    /**
     * Connect an edge between node1 and node2.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply does nothing.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void connect(int node1, int node2) {
        if (this.hasEdge(node1, node2)) {
            return;
        }
        INodeData vertex1 = this.getNode(node1);
        INodeData vertex2 = this.getNode(node2);
        if (vertex1 == null || vertex2 == null) {
            return;
        }
        this.modeCount++;
        this.edges.add(new UndirectedEdge(node1, node2));
        vertex1.addNi(vertex2);
        vertex2.addNi(vertex1);
    }

    /**
     * This method return a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<INodeData> getV() {
        return this.vertexSet;
    }

    /**
     * This method return a collection of  the
     * collection representing all the nodes connected to node_id
     * Note: this method should run in O(1) time.
     *
     * @param node_id
     * @return Collection<node_data>
     */
    @Override
    public Collection<INodeData> getV(int node_id) {
        INodeData node = this.getNode(node_id);
        if (node != null) {
            return node.getNi();
        }
        return null;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public INodeData removeNode(int key) {
        INodeData node = this.getNode(key);
        if (node == null) {
            return null;
        }
        this.modeCount++;
        this.vertexMap.remove(key);
        this.vertexSet.remove(node);
        this.removeEdgesOfVertex(key);
        return node;
    }

    private void removeEdgesOfVertex(int vertex) {
        Set<UndirectedEdge> edgesSet = new HashSet<>(this.edges);
        for (UndirectedEdge edge : edgesSet) {
            if (edge.contains(vertex)) {
                this.removeEdge(edge.left(),edge.right());
            }
        }
    }

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        this.modeCount++;
        this.edges.remove(new UndirectedEdge(node1, node2));
    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return this.vertexSet.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.edges.size();
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.modeCount;
    }

    /**
     * Gets edge length.
     *
     * @param u the first vertex
     * @param v the second vertex
     * @return the edge length. if no edge exists return infinity.
     */
    @Override
    public double getEdgeLength(int u, int v) {
        return 1;
    }

    public String stringifyVertexes() {
        StringBuilder sb = new StringBuilder();
        sb.append("V={");
        for (INodeData v : this.vertexSet) {
            sb.append(String.format("%d, ", v.getKey()));
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }

    public String stringifyEdges() {
        StringBuilder sb = new StringBuilder();
        sb.append("E={");
        int k = 0;
        for (UndirectedEdge edge : this.edges) {
            if (k == 10) {
                k = 0;
                sb.append("\n\t");
            }
            sb.append(edge.toString()).append(", ");
            k++;
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }

    public String stringifyEdgesAsMatrix() {
        int[] vertexesNumbers = new int[this.vertexSet.size()];
        int k = 0;
        for (INodeData v : this.vertexSet) {
            vertexesNumbers[k++] = v.getKey();
        }
        Arrays.sort(vertexesNumbers);
        String[][] matrix = new String[this.vertexSet.size() + 1][this.vertexSet.size() + 1];
        matrix[0][0] = " ";
        for (int i = 1; i < matrix.length; ++i) {
            matrix[0][i] = Integer.toString(vertexesNumbers[i - 1]);
            matrix[i][0] = Integer.toString(vertexesNumbers[i - 1]);
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                matrix[i][j] = "f";
                matrix[j][i] = "f";
            }
        }
        for (int i = 0; i < vertexesNumbers.length; ++i) {
            for (int j = 0; j < vertexesNumbers.length; ++j) {
                if (this.edges.contains(new UndirectedEdge(vertexesNumbers[i], vertexesNumbers[j]))) {
                    matrix[i + 1][j + 1] = "t";
                    matrix[j + 1][i + 1] = "t";
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                sb.append(" ").append(matrix[j][i]).append("|").append(" ");
            }
            sb.deleteCharAt(sb.lastIndexOf("| "));
            sb.append("\n").append("----".repeat(matrix[i].length)).append("\n");
        }
        sb.delete(sb.lastIndexOf("\n" + "----".repeat(matrix.length)), sb.length());
        return sb.toString();
    }

    @Override
    public String toString() {
        if (this.toString == null) {
            this.toString = String.format("\n\nUndirectedGraph:\nMode Count:%d\n\n%s\n%s\n\n%s\n", this.modeCount, this.stringifyVertexes(),
                    this.stringifyEdges(), this.stringifyEdgesAsMatrix());
        }
        return this.toString;
    }


}

