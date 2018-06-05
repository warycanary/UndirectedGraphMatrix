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
		final int srcIndex = vertices.indexOf(srcLabel);
		final int tarIndex = vertices.indexOf(tarLabel);
		final int edgeIndex = getEdgeIndex(srcLabel, tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println(ErrorMessages.values()[4]);
		/* Checks if vertices exist */
		} else if (srcIndex != doesNotExist && tarIndex != doesNotExist) {
			/* If edge does not exist */
			if (edgeIndex == doesNotExist) {
				/* Adds a column */
				ArrayList<Integer> column = new ArrayList<>();
				for (T vertex : vertices.getAll()) {
					if (vertex.equals(srcLabel) || vertex.equals(tarLabel)) {
						column.add(1);
					} else {
						column.add(0);
					}
				}
				matrix.add(column);
			} else {
				System.err.println(ErrorMessages.values()[3]);
			}
		} else {
			System.err.println(ErrorMessages.values()[2]);
		}
	}
	
	/* Returns the index of the address in the matrix */
	private int getEdgeIndex(T srcLabel, T tarLabel) {
		final int srcIndex = vertices.indexOf(srcLabel);
		final int tarIndex = vertices.indexOf(tarLabel);
		
		for (int i = 0; i < matrix.size(); i++) {
			if (matrix.get(i).get(srcIndex) == 1 && matrix.get(i).get(tarIndex) == 1) {
				return i;
			}
		}
		return doesNotExist;
	}
	
	/* Returns an ArrayList of T object neighbours for a given vertex index */
	public List<T> neighbours(int vertIndex) {
		List<T> neighbours = new ArrayList<>();
		/* Checks if vertex exists */
		if (vertIndex != doesNotExist) {
			/* For each edge column in the matrix */
			for (List<Integer> column : matrix) {
				/* For all edges of the current vertex */
				if (column.get(vertIndex).equals(1)) {
					/* For all vertices of the current edge */
					for (int i = 0; i < column.size(); i++) {
						/* Gets the neighbour and not the current vertex */
						if (column.get(i).equals(1) && i != vertIndex) {
							neighbours.add(vertices.get(i));
						}
					}
				}
			}
		} else {
			System.err.println(ErrorMessages.values()[8]);
		}
		return neighbours;
	}
	
	/* Removes a vertex and associated edges (Deletes array positions) */
	public void removeVertex(T vertLabel) {
		final int vertIndex = vertices.indexOf(vertLabel);
		
		/* Checks if vertex exists */
		if (vertIndex != doesNotExist) {
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
			System.err.println(ErrorMessages.values()[1]);
		}
	}
	
	/* Removes edge column from matrix and edge collection from edges */
	public void removeEdge(T srcLabel, T tarLabel) {
		final int srcIndex = vertices.indexOf(srcLabel);
		final int tarIndex = vertices.indexOf(tarLabel);
		final int edgeIndex = getEdgeIndex(srcLabel, tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println(ErrorMessages.values()[7]);
		/* Checks if vertices exists */
		} else if (srcIndex != doesNotExist && tarIndex != doesNotExist) {
			/* Checks if edge exists */
			if (edgeIndex != doesNotExist) {
				matrix.remove(edgeIndex);
			} else {
				System.err.println(ErrorMessages.values()[6]);
			}
		} else {
			System.err.println(ErrorMessages.values()[5]);
		}
	}
	
	/* Prints a formatted list of edges */
	public void printEdges(PrintStream os){
		for (List<Integer> column : matrix) {
			for (int i = 0; i < column.size(); i++) {
				if (column.get(i) == 1) {
					os.printf("%s ", vertices.get(i));
				}
			}
			os.println();
			
			/* Prints edge in reverse order */
			for (int i = column.size() - 1; i >= 0; i--) {
				if (column.get(i) == 1) {
					os.printf("%s ", vertices.get(i));
				}
			}
			os.println();
		}
	}
	
}
