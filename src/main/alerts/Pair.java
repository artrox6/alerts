package alerts;

import java.util.Objects;

public class Pair<K extends String, V extends String> {

    private final K source;

    private final V target;

    public Pair(K source, V target) {
        this.source = source;
        this.target = target;
    }

    public Pair() {
        this.source = null;
        this.target = null;
    }

    public K getSource() {
        return source;
    }

    public V getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(source, pair.source) && Objects.equals(target, pair.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }
}
