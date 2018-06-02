import java.io.*;
import java.util.*;

/**
 * Skeleton code by Jeffrey Chan, 2016.
 * Implementation by Sean Martin 2018
 */
public class IndMatrix<T> extends Matrix<T> {
	
	/* Adds a new row to each edge column */
	public void expandMatrix() {
		for (List<Integer> column : matrix) {
			column.add(0);
		}
	}
	
	/* Adds a new edge to the matrix */
	public void addEdge(T srcLabel, T tarLabel) {
		
		final int srcIndex = getVertexIndex(srcLabel);
		final int tarIndex = getVertexIndex(tarLabel);
		final int edgeIndex = getEdgeIndex(srcLabel, tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println("Error: Can not add edge. A vertex can not have"
					+ " an edge with itself.");
		/* Checks if vertices exist */
		} else if (srcIndex != doesNotExist && tarIndex != doesNotExist) {
			
			/* If edge does not exist */
			if (edgeIndex == doesNotExist) {
				
				/* Adds a column */
				ArrayList<Integer> column = new ArrayList<>();
				for (T vertex : vertices) {
					if (vertex.equals(srcLabel) || vertex.equals(tarLabel))
						column.add(1);
					else
						column.add(0);
				}
				matrix.add(column);
				
			} else {
				System.err.println("Error: Can not add edge. Edge already exists.");
			}
		} else {
			System.err.println("Error: Can not add edge. Vertex does not exist.");
		}
		
	} // end of addEdge()
	
	/* Returns the index of the address in the matrix */
	private int getEdgeIndex(T srcLabel, T tarLabel) {
		
		final int srcIndex = getVertexIndex(srcLabel);
		final int tarIndex = getVertexIndex(tarLabel);
		
		for (int i = 0; i < matrix.size(); i++) {
			if (matrix.get(i).get(srcIndex) == 1 && matrix.get(i).get(tarIndex) == 1) {
				return i;
			}
		}
		return doesNotExist;
	}

	/* Returns an ArrayList of T object neighbours for a given T object */
	public ArrayList<T> neighbours(T vertLabel) {
		ArrayList<T> neighbours = new ArrayList<>();
		final int vertIndex = getVertexIndex(vertLabel);
		
		/* Checks if vertex exists */
		if (vertIndex != doesNotExist) {
			/* For each edge column */
			for (List<Integer> column : matrix) {
				/* If the edge involves the requested vertex */
				if (column.get(vertIndex) == 1) {
					/* For all vertices in the edge column */
					for (int i = 0; i < column.size(); i++) {
						/* Finds the corresponding vertex and not the requested vertex */
						if (column.get(i) == 1 && i != vertIndex) {
							neighbours.add(vertices.get(i));
						}
					}
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
			
			/* Remove edge columns */
			List<List<Integer>> remove = new ArrayList<>();
			for (List<Integer> column : matrix) {
				if (column.get(vertIndex) == 1) {
					remove.add(column);
				}
			}
			matrix.removeAll(remove);
			
			/* Removed vertex row in each column */
			for (List<Integer> column : matrix) {
				column.remove(vertIndex);
			}
			/* Remove vertex from vertex collection */
			vertices.remove(vertLabel);
			
		} else {
			System.err.println("Error: Can not remove vertex. Vertex does not exist.");
		}
		
	} // end of removeVertex()
	
	/* Removes edge column from matrix and edge collection from edges */
	public void removeEdge(T srcLabel, T tarLabel) {
		
		final int srcIndex = getVertexIndex(srcLabel);
		final int tarIndex = getVertexIndex(tarLabel);
		final int edgeIndex = getEdgeIndex(srcLabel, tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println("Error: Can not remove edge. A vertex can not have"
					+ " an edge with itself.");
		/* Checks if vertices exists */
		} else if (srcIndex != doesNotExist && tarIndex != doesNotExist) {
			
			/* Checks if edge exists */
			if (edgeIndex != doesNotExist) {
				matrix.remove(edgeIndex);
			} else {
				System.err.println("Error: Can not remove edge. Edge does not exist.");
			}
			
		} else {
			System.err.println("Error: Can not remove edge. Vertices do not exist.");
		}
	} // end of removeEdges()
	
	/* Prints a formatted list of Vertices */
	public void printVertices(PrintWriter os) {
		for (T print : vertices) {
			os.printf("%s ", print.toString());
		}
		os.println();
		os.flush();
	} // end of printVertices()
	
	/* Prints a formatted list of edges */
	public void printEdges(PrintWriter os) {
		for (List<Integer> column : matrix) {
			for (int i = 0; i < column.size(); i++) {
				if (column.get(i) == 1) {
					os.printf("%s ", vertices.get(i));
				}
			}
			os.println();
		}
		
		/* Prints edges in reverse order */
		for (List<Integer> column : matrix) {
			for (int i = column.size() - 1; i >= 0; i--) {
				if (column.get(i) == 1) {
					os.printf("%s ", vertices.get(i));
				}
			}
			os.println();
		}
		os.flush();
	} // end of printEdges()
	
	/* BFS queue updating */
	public void updateQueue(T vertex, Queue<T> queue, List<T> parents, int vertIndex) {
		/* For each edge */
		for (List<Integer> column : matrix) {
			/* For all edges of the current vertex*/
			if (column.get(vertIndex).equals(1)) {
				/* Checks the vertices of the current edge */
				for (int i = 0; i < column.size(); i++) {
					/* Adds neighbouring vertex to queue and sets their
					 * parent to the current vertex if the vertex being
					 * checked is not the current vertex, is not already
					 * in the queue and had not been visited yet
					 * (parent is null). */
					if (column.get(i).equals(1)
					&& vertIndex != i
					&& parents.get(i) == null
					&& !queue.contains(vertices.get(i))) {
						
						queue.add(vertices.get(i));
						parents.set(i, vertex);
					}
				}
			}
		}
	}
	
	public int getEdgeCount() {
		return super.matrix.size();
	}
	
} // end of class IndMatrix
