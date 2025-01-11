import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WeightedGraph {
    int numOfVertex;
    int[][] matrix;

    /**
     * Initializes an empty graph with the given number of vertices.
     * 
     * @param numOfVertex Number of vertices.
     */
    public WeightedGraph(int numOfVertex) {
        setGraph(numOfVertex);
    }

    /**
     * Creates a graph by reading data from a file.
     *
     * @param fileName File containing the graph data.
          * @throws FileNotFoundException 
          */
         public WeightedGraph(String fileName) throws FileNotFoundException {
        
        try {
            readFile(fileName);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }

    /**
     * Reads and populates the graph from a file.
     *
     * @param fileName Name of the file.
          * @throws FileNotFoundException 
          */
         private void readFile(String fileName) throws FileNotFoundException {
        try {
            File file = new File(fileName);
            Scanner scnr = new Scanner(file);

            // Get first item in file which is the number of vertex
            String vertexStr = scnr.nextLine();
            int vertexInt = Integer.parseInt(vertexStr);
            setGraph(vertexInt);

            // if next line exist.
            while (scnr.hasNextLine()) {
                String edge = scnr.nextLine();
                String[] matrixStr = edge.split(" ");

                int vertex1 = Integer.parseInt(matrixStr[0]);
                int vertex2 = Integer.parseInt(matrixStr[1]);
                int weight = Integer.parseInt(matrixStr[2]);

                // insertValue into graph.
                insertValue(vertex1, vertex2, weight);
            }
            scnr.close();
        } catch (FileNotFoundException err) {
            throw new FileNotFoundException();
        }
    }

    /**
     * Sets up the adjacency matrix and vertex count.
     *
     * @param numOfVertex Number of vertices.
     */
    private void setGraph(int numOfVertex) {
        this.numOfVertex = numOfVertex;
        this.matrix = new int[numOfVertex][numOfVertex];
    }

    /**
     * Adds an edge with the given weight to the matrix.
     *
     * @param vertex1 Start vertex.
     * @param vertex2 End vertex.
     * @param weight  Weight of the edge.
     */
    public void insertValue(int vertex1, int vertex2, int weight) {
        this.matrix[vertex1][vertex2] = weight;
    }

    /**
     * Prints all edges of the graph with their weights.
     */
    public void print() {
        System.out.println("Here is a minimal spanning tree:");
        for (int i = 0; i < this.numOfVertex; i++) {
            for (int j = 0; j < this.numOfVertex; j++) {
                if (matrix[i][j] > 0) {
                    int weight = matrix[i][j];
                    System.out.println("Edge (" + i + ", " + j + "), weight " + weight);
                }
            }
        }
    }

    /**
     * Calculates the total weight of all edges in the graph.
     *
     * @return Sum of edge weights.
     */
    public int weightSum() {
        int sum = 0;
        for (int i = 0; i < this.numOfVertex; i++) {
            for (int j = 0; j < this.numOfVertex; j++) {
                if (matrix[i][j] > 0) {
                    sum += matrix[i][j];
                }
            }
        }
        return sum;
    }
}