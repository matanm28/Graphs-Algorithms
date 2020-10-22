import java.util.Collection;

/**
 * This interface represents the set of operations applicable on a
 * node (vertex) in an (undirectional) unweighted graph.
 *
 * @author boaz.benmoshe
 */
public interface INodeData {
	/**
	 * Return the key (id) associated with this node.
	 * Note: each node_data should have a unique key.
	 * @return
	 */
	public int getKey();

	/**
	 * This method returns a collection with all the Neighbor nodes of this node_data */
	public Collection<INodeData> getNi();

    /**
	 * return true iff this<==>key are adjacent, as an edge between them.
	 * @param key
	 * @return
	 */
	public boolean hasNi(int key);

	/**
	 * This method adds the node_data (t) to this node_data.
	 */
	public void addNi(INodeData t);

	/**
	 * Removes the edge this-key,
	 *
	 * @param node
	 */
	public void removeNode(INodeData node);

	/**
	 * return the remark (meta data) associated with this node.
	 *
	 * @return
	 */
	public String getInfo();
	/**
	 * Allows changing the remark (meta data) associated with this node.
	 * @param s
	 */
	public void setInfo(String s);
	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	public int getTag();
	/** 
	 * Allow setting the "tag" value for temporal marking an node - common 
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	public void setTag(int t);
}
