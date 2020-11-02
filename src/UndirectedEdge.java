import java.util.Objects;

public class UndirectedEdge {

    private UnorderedPair<Integer> edge;

    public Integer left() {
        return edge.first();
    }

    public Integer right() {
        return edge.second();
    }

    public UndirectedEdge(Integer vertex1, Integer vertex2) {
        this.edge = new UnorderedPair<>(vertex1, vertex2);
    }

    public boolean contains(int vertex) {
        return this.edge.first().equals(vertex) || this.edge.second().equals(vertex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UndirectedEdge that = (UndirectedEdge) o;
        return Objects.equals(edge, that.edge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edge);
    }

    @Override
    public String toString() {
        return String.format("{%d,%d}", this.edge.first(), this.edge.second());
    }
}
