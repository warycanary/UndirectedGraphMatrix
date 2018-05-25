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
	} // end of expandMatrix()
	
	/* Adds a new edge to the matrix */
	public void addEdge(T srcLabel, T tarLabel) {
		final int srcIndex = getVertexIndex(srcLabel);
		final int tarIndex = getVertexIndex(tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println("Error: Can not add edge. A vertex can not have"
					+ " an edge with itself.");
		/* Checks if vertices exist */
		} else if (srcIndex != doesNotExist  && tarIndex != doesNotExist) {
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
	} // end of addEdges()
	
	/* Returns an ArrayList of T object neighbours for a given T object */
	public ArrayList<T> neighbours(T vertLabel) {
		ArrayList<T> neighbours = new ArrayList<>();
		final int vertIndex = getVertexIndex(vertLabel);
		
		/* Checks if vertex exists */
		if (vertIndex != doesNotExist) {
			/* If an edge exists, add neighbours to the neighbours array */
			for (int i = 0; i < matrix.get(vertIndex).size(); i++) {
				if (matrix.get(vertIndex).get(i).equals(1)) {
					neighbours.add(vertices.get(i));
				}
			}
		} else {
			System.err.println("Error: Can not search neighbours. Vertex does not exist.");
		}
		return neighbours;
	} // end of neighbours()
	
	/* Removes a vertex and associated edges (Deletes array positions) */
	public void removeVertex(T vertLabel) {
		final int vertIndex = getVertexIndex(vertLabel);
		
		/* Checks if vertex exists */
		if (vertIndex != doesNotExist) {
			/* Remove matrix rows */
			for (List<Integer> row : matrix) {
				row.remove(vertIndex);
			}
			/* Remove matrix column */
			matrix.remove(vertIndex);
			/* Remove from vertex HashMap */
			vertices.remove(vertLabel);
		} else {
			System.err.println("Error: Can not remove vertex. Vertex does not exist.");
		}
	} // end of removeVertex()
	
	
	/* Sets edge values in edges matrix to 0 */
	public void removeEdge(T srcLabel, T tarLabel) {
		final int srcIndex = getVertexIndex(srcLabel);
		final int tarIndex = getVertexIndex(tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println("Error: Can not remove edge. A vertex can not have"
					+ " an edge with itself.");
		/* Checks if vertices are valid */
		} else if (srcIndex != doesNotExist && tarIndex != doesNotExist) {
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
	} // end of removeEdges()
	
	/* Prints a formatted list of Vertices */
	public void printVertices(PrintWriter os) {
		for (T print : vertices) {
			os.printf("%s ", print.toString());
		}
		System.out.println();
		os.flush();
	} // end of printVertices()
	
	/* Prints a formatted list of edges */
	public void printEdges(PrintWriter os) {
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(i).size(); j++) {
				if (matrix.get(i).get(j).equals(1)) {
					os.printf("%s %s\n", vertices.get(i), vertices.get(j));
				}
			}
		}
		os.flush();
	} // end of printEdges()
	
	/* BFS Queue updating */
	public void updateQueue(T vertex, Queue<T> queue, List<T> parents, int vertIndex) {
		/* For each neighbour of the current vertex */
		for (int i = 0; i < matrix.get(vertIndex).size(); i++) {
			
			/* Adds neighbouring vertices to queue and sets their parent to the
			 * current vertex if they are not already in the queue and their
			 * parent is null */
			if (matrix.get(vertIndex).get(i).equals(1)
			&& parents.get(i) == null
			&& !queue.contains(vertices.get(i))) {
				
				queue.add(vertices.get(i));
				parents.set(i, vertex);
			}
		}
	}
	
	public int getEdgeCount() {
		return super.matrix.size();
	}

} // end of class AdjMatrix
