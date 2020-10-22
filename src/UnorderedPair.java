import java.util.Objects;

public class UnorderedPair<T extends Comparable<T>> {
    private T first;
    private T second;

    public UnorderedPair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public T second() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnorderedPair<?> that = (UnorderedPair<?>) o;
        return (Objects.equals(first, that.first) && Objects.equals(second, that.second))
                || (Objects.equals(second, that.first) && Objects.equals(first, that.second));
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

}
