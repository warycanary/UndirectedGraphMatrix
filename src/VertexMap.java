import java.util.*;

class VertexMap<T> {
    
    private List<T> vertices;
    private Map<T, Integer> vertexMap;
    
    VertexMap() {
        vertices = new ArrayList<>();
        vertexMap = new HashMap<>();
    }
    
    void add(T vertex) {
        vertexMap.put(vertex, vertices.size());
        vertices.add(vertex);
    }
    
    void remove(T vertex) {
        int index = vertexMap.get(vertex);
        vertices.remove(index);
        vertexMap.remove(vertex);
        updateMap();
    }
    
    /* Completely reconstruct the map */
    private void updateMap() {
        int index = 0;
        for (T vertex : vertices) {
            vertexMap.put(vertex, index++);
        }
    }
    
    Collection<T> getAll() {
        return this.vertices;
    }
    
    int size() {
        return this.vertices.size();
    }
    
    /* Gets the vertex at an index */
    T get(int index) {
        return vertices.get(index);
    }
    
    /* Gets the index of a vertex. */
    int indexOf(T vertex) {
        try {
            return vertexMap.get(vertex);
        /* Vertex does not exist */
        } catch (NullPointerException e) {
            return -1;
        }
    }
    
}
