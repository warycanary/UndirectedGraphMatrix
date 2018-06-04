import java.util.*;

public abstract class Matrix<T> {
	
	private final int disconnectedDist = -1;
	
	/* 2D ArrayList of Integers representing a matrix */
	List<List<Integer>> matrix;
	/* Map of vertices */
	VertexMap<T, Integer> vertices;
	
	public Matrix() {
		vertices = new VertexMap<>();
		matrix = new ArrayList<>();
	}
	
	/* Adds a vertex to vertex collection and expands the matrix */
	public void addVertex(T vertLabel) {
		final Integer vertIndex = vertices.get(vertLabel);
		
		/* Checks if vertex does not exist */
		if (vertIndex == null) {
			vertices.put(vertLabel, vertices.size());
			expandMatrix();
		} else {
			System.err.println("Error: Can not add vertex. Vertex already exists.");
		}
	}
	
	/* Returns an ArrayList of T object neighbours for a given T object */
	public List<T> neighbours(T vertLabel) {
		final Integer vertIndex = vertices.get(vertLabel);
		return neighbours(vertIndex);
	}
	
	/* Calculates the shortest path between to vertices */
	public int shortestPath(T vertLabel1, T vertLabel2) {
		final Integer vertIndex1 = vertices.get(vertLabel1);
		final Integer vertIndex2 = vertices.get(vertLabel2);
		
		/* Checks if vertices exist */
		if (vertIndex1 != null && vertIndex2 != null) {
			/* Creates queue of vertices to explore */
			Queue<T> queue = new LinkedList<>();
			queue.add(vertLabel1);
			
			/* Create parents array. Vertices with parents have been visited */
			List<T> parents = new ArrayList<>(vertices.size());
			for (int i = 0; i < vertices.size(); i++) {
				parents.add(null);
			}
			
			/* Loops until queue is empty */
			while (queue.size() > 0) {
				/* If destination is found in the queue, calculate the path */
				if (queue.contains(vertLabel2)) {
					return shortestPath(vertLabel1, vertLabel2, parents);
				}
				/* Remove the current vertex from the queue, find neighbours and update queue */
				T vertex = queue.poll();
				final Integer vertIndex = vertices.get(vertex);
				updateQueue(vertex, vertIndex, queue, parents);
			}
			
		} else {
			System.err.println("Error: Can not calculate path. Vertex does not exist.");
		}
		
		/* If the queue is empty, all vertices have been explored and the
		 * source and target are disconnected */
		return disconnectedDist;
	}
	
	/* Adds neighbours of the current vertex to the queue and sets their parents */
	private void updateQueue(T vertex, Integer vertIndex, Queue<T> queue, List<T> parents) {
		/* For all neighbours of the current vertex */
		for (T neighbour : neighbours(vertIndex)) {
			final int neighIndex = vertices.get(neighbour);
			/* If the neighbour has not been explored and is not in the queue */
			if (parents.get(neighIndex) == null && !queue.contains(neighbour)) {
				/* Adds neighbouring vertex to queue and sets parent to the current vertex */
				queue.add(neighbour);
				parents.set(neighIndex, vertex);
			}
		}
	}
	
	/* Traverses the parents array and calculates the shortest path */
	private int shortestPath(T source, T current, List<T> parents) {
		int path = 0;
		while (!current.equals(source)) {
			current = parents.get(vertices.get(current));
			path++;
		}
		return path;
	}
	
	public int getEdgeCount() {
		return matrix.size();
	}
	
	public int getVertexCount() {
		return vertices.size();
	}
	
	abstract void expandMatrix();
	abstract void addEdge(T srcLabel, T tarLabel);
	abstract List<T> neighbours(Integer vertIndex);
	abstract void removeVertex(T vertLabel);
	abstract void removeEdge(T srcLabel, T tarLabel);
}
