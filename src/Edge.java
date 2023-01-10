// Arjun Sharma (251240847)
// The edge class, makes an edge between 2 nodes and stores the type of edge
// NOTE: these are unordered edges, so the first node may not be the start

public class Edge {
    private Node node1; // first node in the edge
    private Node node2; // second node in the edge
    private String type; // type of the edge

    // Constructor, creates an edge between node1 and node2 and sets the type
    public Edge(Node node1, Node node2, String type) {
        this.node1 = node1;
        this.node2 = node2;
        this.type = type;
    }

    // Returns the first node in the edge
    public Node firstNode() {
        return node1;
    }

    // Returns the second node in the edge
    public Node secondNode() {
        return node2;
    }

    // Returns the type of the edge
    public String getType() {return type;}
}
