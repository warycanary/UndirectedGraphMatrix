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
		final int srcIndex = vertices.indexOf(srcLabel);
		final int tarIndex = vertices.indexOf(tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println(ErrorMessages.values()[4]);
		/* Checks if vertices exist */
		} else if (srcIndex != doesNotExist  && tarIndex != doesNotExist) {
			/* Checks if edge does not exist then sets edge */
			if (matrix.get(srcIndex).get(tarIndex).equals(0)
			&& matrix.get(tarIndex).get(srcIndex).equals(0)) {
				matrix.get(srcIndex).set(tarIndex, 1);
				matrix.get(tarIndex).set(srcIndex, 1);
			} else {
				System.err.println(ErrorMessages.values()[3]);
			}
		} else {
			System.err.println(ErrorMessages.values()[2]);
		}
	}
	
	/* Returns an ArrayList of T object neighbours for a given T object */
	public List<T> neighbours(int vertIndex) {
		List<T> neighbours = new ArrayList<>();
		
		/* Checks if vertex exists */
		if (vertIndex != doesNotExist) {
			/* If an edge exists, add neighbours to the neighbours array */
			for (int i = 0; i < matrix.get(vertIndex).size(); i++) {
				if (matrix.get(vertIndex).get(i).equals(1)) {
					neighbours.add(vertices.get(i));
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
			/* Remove matrix rows */
			for (List<Integer> row : matrix) {
				row.remove(vertIndex);
			}
			/* Remove matrix column */
			matrix.remove(vertIndex);

			/* Remove from vertex collection */
			vertices.remove(vertLabel);
		} else {
			System.err.println(ErrorMessages.values()[1]);
		}
	}
	
	/* Sets edge values in edges matrix to 0 */
	public void removeEdge(T srcLabel, T tarLabel) {
		final int srcIndex = vertices.indexOf(srcLabel);
		final int tarIndex = vertices.indexOf(tarLabel);
		
		/* Checks if the vertices provided are the same. */
		if (srcLabel.equals(tarLabel)) {
			System.err.println(ErrorMessages.values()[7]);
		/* Checks if vertices are valid */
		} else if (srcIndex != doesNotExist && tarIndex != doesNotExist) {
			/* Checks if edge exists */
			if (matrix.get(srcIndex).get(tarIndex).equals(1)
			&& matrix.get(tarIndex).get(srcIndex).equals(1)) {
				matrix.get(srcIndex).set(tarIndex, 0);
				matrix.get(tarIndex).set(srcIndex, 0);
			} else {
				System.err.println(ErrorMessages.values()[6]);
			}
		} else {
			System.err.println(ErrorMessages.values()[5]);
		}
	}
	
	/* Prints a formatted list of edges */
	public void printEdges(PrintStream os) {
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(i).size(); j++) {
				if (matrix.get(i).get(j).equals(1)) {
					os.printf("%s %s\n", vertices.get(i), vertices.get(j));
				}
			}
		}
		os.flush();
	}

}