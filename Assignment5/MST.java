
/*
* COMP 2140 SECTION A02
* INSTRUCTOR: Dr. Lauren Himbeault
* STUDENT NUMBER: 7980132
* ASSIGNMENT: Assignment 5
* QUESTION: Question 
*
* PURPOSE: Minimal spanning tree.
*/
import java.io.FileNotFoundException;

public class MST {
    /**
     * Entry point of the program. Processes the MST based on the input file.
     *
     * @param args Command-line arguments (expects the file name as the first
     *             argument).
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Invalid Argument!!");
        } else {
            processMST(args[0]);
        }

        System.out.println("\nProgram terminated succesfully.");
    }

    /**
     * Reads a graph from a file, generates its MST, and prints the result.
     *
     * @param fileName Name of the file containing graph data.
     */

    private static void processMST(String fileName) {
        WeightedGraph graphG;
        try {
            graphG = new WeightedGraph(fileName);
            // From the graph generate the minimum spanning tree;
            WeightedGraph graphT = generateMST(graphG);
            graphT.print();
            System.out.println("The minimal weight of a spanning tree is " + graphT.weightSum());
        } catch (FileNotFoundException e) {
            System.out.println("File not found !!");
        }

    }

    /**
     * Generates the Minimum Spanning Tree (MST) for the given graph using Prim's
     * algorithm.
     *
     * @param graphG Input graph.
     * @return The MST as a WeightedGraph.
     */
    private static WeightedGraph generateMST(WeightedGraph graphG) {
        WeightedGraph graphT = new WeightedGraph(graphG.numOfVertex);
        MinPQWeightedEdge minPQ = new MinPQWeightedEdge();
        String[] vertexAdded = new String[graphG.numOfVertex];

        // Selected vertex
        int selectedVertex = 0;
        addConnectEdges(minPQ, graphG, vertexAdded, selectedVertex);
        vertexAdded[selectedVertex] = "Added";

        while (!minPQ.isEmpty()) {

            // Retrieve the edge lowest wiehgt
            WeightedEdge currMinEdge = minPQ.retrieveMin();

            // Add vertex b and edge (a, b) to T
            if (vertexAdded[currMinEdge.vertex2] != "Added") {
                graphT.insertValue(currMinEdge.vertex1, currMinEdge.vertex2, currMinEdge.weight);
                vertexAdded[currMinEdge.vertex2] = "Added";
            }

            addConnectEdges(minPQ, graphG, vertexAdded, currMinEdge.vertex2);
        }

        return graphT;
    }

    /**
     * Adds all edges connected to a selected vertex into the priority queue.
     *
     * @param minPQWeightedEdge Priority queue for edges.
     * @param graphG            Input graph.
     * @param vertexAdded       Array tracking added vertices.
     * @param selectedVertex    The currently selected vertex.
     */
    private static void addConnectEdges(MinPQWeightedEdge minPQWeightedEdge, WeightedGraph graphG, String[] vertexAdded,
            int selectedVertex) {
        for (int i = 0; i < graphG.numOfVertex; i++) {
            int weight = graphG.matrix[selectedVertex][i];
            // Find all edges that connect in Graph G
            if (vertexAdded[i] != "Added" && weight > 1) {
                WeightedEdge newEdge = new WeightedEdge(selectedVertex, i, weight);
                minPQWeightedEdge.insert(newEdge);
            }
        }

    }
}
