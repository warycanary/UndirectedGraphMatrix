import java.io.*;
import java.util.*;

public class AdjMatrix<T> extends Matrix<T> {
	
	/* Expands the ArrayList matrix when a new vertex is added */
	public void expandMatrix() {
		/* Expands row on previous columns */
		for (List<Integer> row : matrix) {
			row.add(0);
		}
		/* Builds and adds a new column */
		List<Integer> column = new ArrayList<>();
		for (int i = 0; i < vertices.size(); i++) {
			column.add(0);
		}
		matrix.add(column);
	}
	
	/* Adds a new edge to the matrix */
	public void addEdge(T srcLabel, T tarLabel) {
		final Integer srcIndex = vertices.get(srcLabel);
		final Integer tarIndex = vertices.get(tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println("Error: Can not add edge. A vertex can not have"
					+ " an edge with itself.");
		/* Checks if vertices exist */
		} else if (srcIndex != null  && tarIndex != null) {
			/* Checks if edge does not exist then sets edge */
			if (matrix.get(srcIndex).get(tarIndex).equals(0)
			&& matrix.get(tarIndex).get(srcIndex).equals(0)) {
				matrix.get(srcIndex).set(tarIndex, 1);
				matrix.get(tarIndex).set(srcIndex, 1);
			} else {
				System.err.println("Error: Can not add edge. Edge already exists.");
			}
		} else {
			System.err.println("Error: Can not add edge. Vertex does not exist.");
		}
	}
	
	/* Returns an ArrayList of T object neighbours for a given T object */
	public List<T> neighbours(Integer vertIndex) {
		ArrayList<T> neighbours = new ArrayList<>();
		
		/* Checks if vertex exists */
		if (vertIndex != null) {
			/* If an edge exists, add neighbours to the neighbours array */
			for (int i = 0; i < matrix.get(vertIndex).size(); i++) {
				if (matrix.get(vertIndex).get(i).equals(1)) {
					neighbours.add(vertices.getVertex(i));
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
			/* Remove matrix rows */
			for (List<Integer> row : matrix) {
				row.remove(vertIndex);
			}
			/* Remove matrix column */
			matrix.remove(vertIndex);

			/* Remove from vertex collection */
			vertices.remove(vertLabel);
		} else {
			System.err.println("Error: Can not remove vertex. Vertex does not exist.");
		}
	}
	
	/* Sets edge values in edges matrix to 0 */
	public void removeEdge(T srcLabel, T tarLabel) {
		final Integer srcIndex = vertices.get(srcLabel);
		final Integer tarIndex = vertices.get(tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println("Error: Can not remove edge. A vertex can not have"
					+ " an edge with itself.");
		/* Checks if vertices are valid */
		} else if (srcIndex != null && tarIndex != null) {
			/* Checks if edge exists */
			if (matrix.get(srcIndex).get(tarIndex).equals(1)
			&& matrix.get(tarIndex).get(srcIndex).equals(1)) {
				matrix.get(srcIndex).set(tarIndex, 0);
				matrix.get(tarIndex).set(srcIndex, 0);
			} else {
				System.err.println("Error: Can not remove edge. Edge does not exist.");
			}
		} else {
			System.err.println("Error: Can not remove edge. Vertex does not exist.");
		}
	}
	
	/* Prints a formatted list of VertexCollection */
	public void printVertices(PrintWriter os) {
		for (T print : vertices.keySet()) {
			os.printf("%s ", print.toString());
		}
		System.out.println();
		os.flush();
	}
	
	/* Prints a formatted list of edges */
	public void printEdges(PrintWriter os) {
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(i).size(); j++) {
				if (matrix.get(i).get(j).equals(1)) {
					os.printf("%s %s\n", vertices.getVertex(i), vertices.getVertex(j));
				}
			}
		}
		os.flush();
	}

}