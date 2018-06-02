import java.util.*;

public abstract class Matrix<T> {
	
	protected final int disconnectedDist = -1, doesNotExist = -1;
	
	/* 2D ArrayList of Integers representing an adjacency matrix */
	List<List<Integer>> matrix;
	
	/* Collection for storing vertices */
	List<T> vertices;
	
	public Matrix() {
		vertices = new ArrayList<>();
		matrix = new ArrayList<>();
	}
	
	/* Adds a vertex to vertex collection and expands the matrix */
	public void addVertex(T vertLabel) {
		
		final int vertIndex = getVertexIndex(vertLabel);
		
		/* Checks if vertex does not exist */
		if (vertIndex == doesNotExist) {
			vertices.add(vertLabel);
			expandMatrix();
		} else {
			System.err.println("Error: Can not add vertex. Vertex already exists.");
		}
	} // end of addVertex()
	
	/* Calculates the shortest path between to vertices */
	public int shortestPathDistance(T vertLabel1, T vertLabel2) {
		final int vertIndex1 = getVertexIndex(vertLabel1);
		final int vertIndex2 = getVertexIndex(vertLabel2);
		
		/* Checks if vertices exist */
		if (vertIndex1 != doesNotExist && vertIndex2 != doesNotExist) {
			Queue<T> queue = new LinkedList<>();
			queue.add(vertLabel1);
			
			/* Vertices with parents have been visited */
			List<T> parents;
			parents = new ArrayList<>(vertices.size());
			for (int i = 0; i < vertices.size(); i++) {
				parents.add(null);
			}
			
			/* Loops until queue is empty */
			while (queue.size() > 0) {
				
				/* If destination is found in the queue, calculate the path */
				if (queue.contains(vertLabel2)) {
					return shortestPath(vertLabel1, vertLabel2, parents);
				}
				
				/* Remove the current vertex from the queue */
				T vertex = queue.poll();
				final int vertIndex = getVertexIndex(vertex);
				
				updateQueue(vertex, queue, parents, vertIndex);
			}
		} else {
			System.err.println("Error: Can not calculate path. Vertex does not exist.");
		}
		
		/* If the queue is empty, all vertices have been explored and the
		 * source and target are disconnected */
		return disconnectedDist;
	} // end of shortestPathDistance()
	
	/* Traverses the parents array and calculates the shortest path */
	private int shortestPath(T source, T current, List<T> parents) {
		int path = 0;
		while (!current.equals(source)) {
			current = parents.get(getVertexIndex(current));
			path++;
		}
		return path;
	}
	
	public int getVertexCount() {
		return vertices.size();
	}
	
	/* Returns the index of the vertex in the vertices array for a given T
	 * object*/
	protected int getVertexIndex(T search) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).equals(search)) {
				return i;
			}
		}
		/* Should never reach */
		return doesNotExist;
	}
	
	public abstract int getEdgeCount();
	public abstract void expandMatrix();
	public abstract void addEdge(T srcLabel, T tarLabel);
	public abstract List<T> neighbours(T vertLabel);
	public abstract void removeVertex(T vertLabel);
	public abstract void removeEdge(T srcLabel, T tarLabel);
	public abstract void updateQueue(T vertex, Queue<T> queue, List<T> parents, int vertIndex);
}
