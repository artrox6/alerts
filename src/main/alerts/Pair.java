package alerts;

import java.util.Objects;

public class Pair<K extends String, V extends String> {

    private final K source;

    private final V target;

    public Pair(K source, V target) {
        this.source = source;
        this.target = target;
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

    @Override
    public String toString() {
        return "Pair{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
