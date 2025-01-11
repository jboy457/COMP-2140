public class WeightedEdge implements Comparable<WeightedEdge>{
    public int vertex1;
    public int vertex2;
    public int weight;

    public WeightedEdge(int vertex1, int vertex2, int weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    @Override
    public int compareTo(WeightedEdge otherEdge) {
        return this.weight - otherEdge.weight;
    }
}