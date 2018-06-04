import java.io.*;
import java.util.*;

public class IndMatrix<T> extends Matrix<T> {
	
	/* Adds a new row to each edge column */
	public void expandMatrix() {
		for (List<Integer> column : matrix) {
			column.add(0);
		}
	}
	
	/* Adds a new edge to the matrix */
	public void addEdge(T srcLabel, T tarLabel) {
		final Integer srcIndex = vertices.get(srcLabel);
		final Integer tarIndex = vertices.get(tarLabel);
		final Integer edgeIndex = getEdgeIndex(srcLabel, tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println("Error: Can not add edge. A vertex can not have an edge with itself.");
		/* Checks if vertices exist */
		} else if (srcIndex != null && tarIndex != null) {
			/* If edge does not exist */
			if (edgeIndex == null) {
				/* Adds a column */
				ArrayList<Integer> column = new ArrayList<>();
				for (T vertex : vertices.keySet()) {
					if (vertex.equals(srcLabel) || vertex.equals(tarLabel)) {
						column.add(1);
					} else {
						column.add(0);
					}
				}
				matrix.add(column);
			} else {
				System.err.println("Error: Can not add edge. Edge already exists.");
				System.out.println(srcLabel.toString() + " " + tarLabel.toString() + "\n");
			}
		} else {
			System.err.println("Error: Can not add edge. Vertex does not exist.");
		}
	}
	
	/* Returns the index of the address in the matrix */
	private Integer getEdgeIndex(T srcLabel, T tarLabel) {
		final Integer srcIndex = vertices.get(srcLabel);
		final Integer tarIndex = vertices.get(tarLabel);
		
		for (int i = 0; i < matrix.size(); i++) {
			if (matrix.get(i).get(srcIndex) == 1 && matrix.get(i).get(tarIndex) == 1) {
				return i;
			}
		}
		return null;
	}
	
	/* Returns an ArrayList of T object neighbours for a given T object */
	public List<T> neighbours(T vertLabel) {
		final Integer vertIndex = vertices.get(vertLabel);
		return neighbours(vertIndex);
	}
	
	/* Returns an ArrayList of T object neighbours for a given vertex index */
	public ArrayList<T> neighbours(Integer vertIndex) {
		ArrayList<T> neighbours = new ArrayList<>();
		/* Checks if vertex exists */
		if (vertIndex != null) {
			/* For each edge column in the matrix */
			for (List<Integer> column : matrix) {
				/* For all edges of the current vertex */
				if (column.get(vertIndex).equals(1)) {
					/* For all vertices of the current edge */
					for (int i = 0; i < column.size(); i++) {
						/* Gets the neighbour and not the current vertex */
						if (column.get(i).equals(1) && i != vertIndex) {
							neighbours.add(vertices.getVertex(i));
						}
					}
				}
			}
		} else {
			System.err.println("Error: Can not search neighbours. Vertex does not exist.");
		}
		return neighbours;
	}
	
	/* Removes a vertex and associated edges (Deletes array positions) */
	public void removeVertex(T vertLabel) {
		final Integer vertIndex = vertices.get(vertLabel);
		
		/* Checks if vertex exists */
		if (vertIndex != null) {
			/* Remove edges with other vertexes */
			List<List<Integer>> remove = new ArrayList<>();
			for (List<Integer> column : matrix) {
				if (column.get(vertIndex).equals(1)) {
					remove.add(column);
				}
			}
			matrix.removeAll(remove);
			
			/* Removed vertex row in each column and from vertex collection */
			for (List<Integer> column : matrix) {
				column.remove(vertIndex);
			}
			vertices.remove(vertLabel);
		} else {
			System.err.println("Error: Can not remove vertex. Vertex does not exist.");
		}
	}
	
	/* Removes edge column from matrix and edge collection from edges */
	public void removeEdge(T srcLabel, T tarLabel) {
		final Integer srcIndex = vertices.get(srcLabel);
		final Integer tarIndex = vertices.get(tarLabel);
		final Integer edgeIndex = getEdgeIndex(srcLabel, tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println("Error: Can not remove edge. A vertex can not have"
					+ " an edge with itself.");
		/* Checks if vertices exists */
		} else if (srcIndex != null && tarIndex != null) {
			/* Checks if edge exists */
			if (edgeIndex != null) {
				matrix.remove(edgeIndex);
			} else {
				System.err.println("Error: Can not remove edge. Edge does not exist.");
			}
		} else {
			System.err.println("Error: Can not remove edge. VertexCollection do not exist.");
		}
	}
	
	/* Prints a formatted list of vertices */
	public void printVertices(PrintWriter os) {
		for (T print : vertices.keySet()) {
			os.printf("%s ", print.toString());
		}
		os.println();
		os.flush();
	}
	
	/* Prints a formatted list of edges */
	public void printEdges(PrintWriter os){
		for (List<Integer> column : matrix) {
			for (int i = 0; i < column.size(); i++) {
				if (column.get(i) == 1) {
					os.printf("%s ", vertices.getVertex(i));
				}
			}
			os.println();
			
			/* Prints edge in reverse order */
			for (int i = column.size() - 1; i >= 0; i--) {
				if (column.get(i) == 1) {
					os.printf("%s ", vertices.getVertex(i));
				}
			}
			os.println();
		}
	}
	
}
