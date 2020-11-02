import java.util.*;

public class UndirectedGraph implements IGraph {
    private Map<Integer, INodeData> verticesMap = new HashMap<>();
    private Set<INodeData> vertices = new HashSet<>();
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
        return this.verticesMap.get(key);
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
        if (this.vertices.add(n)){
            this.modeCount++;
            this.verticesMap.put(n.getKey(), n);
        }
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
        if (!this.hasVertex(node1) || !this.hasVertex(node2)) return;
        if (this.edges.add(new UndirectedEdge(node1, node2))) {
            this.modeCount++;
        }
        this.getNode(node1).addNi(this.getNode(node2));
        this.getNode(node2).addNi(this.getNode(node1));
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
        return this.vertices;
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
        Collection<INodeData> ans = new HashSet<>();
        if (this.hasVertex(node_id)){
            ans = this.getNode(node_id).getNi();
        }
        return ans;
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
        this.verticesMap.remove(key);
        this.vertices.remove(node);
        this.removeEdgesOfVertex(key);
        return node;
    }

    private void removeEdgesOfVertex(int vertex) {
        Set<UndirectedEdge> edgesSet = new HashSet<>(this.edges);
        for (UndirectedEdge edge : edgesSet) {
            if (edge.contains(vertex)) {
                this.removeEdge(edge.left(), edge.right());
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
        return this.vertices.size();
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
        for (INodeData v : this.vertices) {
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
        int[] vertexesNumbers = new int[this.vertices.size()];
        int k = 0;
        for (INodeData v : this.vertices) {
            vertexesNumbers[k++] = v.getKey();
        }
        Arrays.sort(vertexesNumbers);
        String[][] matrix = new String[this.vertices.size() + 1][this.vertices.size() + 1];
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

    private boolean hasVertex(int key) {
        return this.getNode(key) != null;
    }


}

