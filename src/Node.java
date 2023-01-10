// Arjun Sharma (251240847)
// A node class, used for the graph, contains a mark for traversal, and id

public class Node {
    private int id; // id, will be between 0 and number of nodes
    private boolean mark; // mark for traversal

    // constructor, setting id and mark
    Node (int id) {
        this.id = id;
        this.mark = false;
    }

    // marking a node, used for traversal
    public void markNode(boolean mark) {this.mark = mark;}

    // getting the mark of the node
    public boolean getMark() {
        return mark;
    }

    // getting the id for the node
    public int getId() {
        return id;
    }
}
