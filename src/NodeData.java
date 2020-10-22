import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NodeData implements INodeData {
    private static int currentKey = 0;
    private int key;
    private Integer tag = null;
    private String data = "";
    private Set<INodeData> neighbors = new HashSet<>();

    public NodeData() {
        this.key = currentKey++;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public Collection<INodeData> getNi() {
        return new HashSet<>(this.neighbors);
    }

    @Override
    public boolean hasNi(int key) {
        for (INodeData node : this.neighbors) {
            if (node.getKey() == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNi(INodeData t) {
        if (t != null) {
            this.neighbors.add(t);
        }
    }

    @Override
    public void removeNode(INodeData node) {
        if (node != null) {
            this.neighbors.remove(node);
        }
    }

    /**
     * return the remark (meta data) associated with this node.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.data;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        if (s != null) {
            this.data = data;
        }
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        if (tag != null) {
            return this.tag;
        }
        return -1;

    }

    /**
     * Allow setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("vertex number:%d\n", this.key));
        if (!this.data.isBlank()) {
            sb.append(String.format("data:%s\n", this.data));
        }
        if (this.tag != null) {
            sb.append(String.format("tag:%d\n", this.tag));
        }
        if (this.neighbors.isEmpty()) {
            sb.append("no neighbors");
        } else {
            sb.append("neighbors:{");
            for (INodeData neighbor : this.neighbors) {
                sb.append(String.format("%d, ", neighbor.getKey()));
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("}");
        }
        return sb.toString();
    }
}
