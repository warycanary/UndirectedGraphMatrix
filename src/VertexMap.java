import java.util.ArrayList;
import java.util.LinkedHashMap;

public class VertexMap<T, Integer> extends LinkedHashMap<T, Integer> {
    
    public T getVertex(int index) {
        return new ArrayList<>(this.keySet()).get(index);
    }
    
}
