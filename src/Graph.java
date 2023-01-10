// Arjun Sharma (251240847)
// Graph class that stores the nodes and edges of the graph in unordered order, and has methods to add nodes and edges
import java.util.ArrayList;
import java.util.Iterator;

public class Graph implements GraphADT{
    private int numNodes; // number of nodes in the graph
    private int numEdges; // number of edges in the graph
    private Node[] nodes; // array of nodes in the graph
    private Edge[] edges; // array of edges in the graph
    private ArrayList<ArrayList<Integer>> adjMatrix; // adjacency matrix of the graph

    // constructor, takes in the number of nodes
    // initializes adjacency matrix and nodes and edges array
    public Graph(int n) {
        numNodes = n; // set number of nodes
        numEdges = 0; // set number of edges to 0, since we have no edges
        nodes = new Node[n]; // initialize nodes array
        edges = new Edge[9999]; // initialize edges array
        adjMatrix = new ArrayList<>(n); // initialize adjacency matrix


        // setting up adjacency matrix, and nodes array
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
            adjMatrix.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                // setting all values to 0, since we have no adjacent nodes
                adjMatrix.get(i).add(0);
            }
        }
    }

    // adding an edge to the graph
    // throws Graph Exception if either node does not exist or if the edge is already in the graph
    public void addEdge(Node node1, Node node2, String edgeType) throws GraphException {

        // if first node not in graph
        if (node1.getId() < 0 || node1.getId() >= numNodes) {throw new GraphException("Node1 is not in the graph");}

        // if second node not in graph
        if (node2.getId() < 0 || node2.getId() >= numNodes) {throw new GraphException("Node2 is not in the graph");}

        // if edge already in graph
        if (adjMatrix.get(node1.getId()).get(node2.getId()) != 0) {throw new GraphException("Edge already exists");}

        // adding edge to adjacency matrix
        adjMatrix.get(node1.getId()).set(node2.getId(), 1);
        adjMatrix.get(node2.getId()).set(node1.getId(), 1);

        // adding edge to edges array
        edges[numEdges] = new Edge(node1, node2, edgeType);

        // incrementing number of edges
        numEdges++;
    }

    // returns the node with the specified id
    // throws a GraphException if the node does not exist
    public Node getNode(int id) throws GraphException {
        // if node not in graph
        if (id < 0 || id >= numNodes) {throw new GraphException("Node is not in the graph"); }

        // returning node
        return nodes[id];
    }


    // returns a Java Iterator storing all the edges incident on the specified node
    // returns Graph Exception if you give node is not in graph, or null if node has no edges incident on it
    public Iterator incidentEdges(Node u) throws GraphException {
        // if node not in graph
        if (u.getId() < 0 || u.getId() >= numNodes) {throw new GraphException("Node is not in the graph");}

        // getting a list of all incident edges
        ArrayList<Edge> incidentEdges = new ArrayList<>();
        for (int i = 0; i < numEdges; i++) {
            if (edges[i].firstNode().getId() == u.getId() || edges[i].secondNode().getId() == u.getId()) {
                incidentEdges.add(edges[i]);
            }
        }

        // if no incident edges, return null
        if (incidentEdges.isEmpty()) return null;

        // returning iterator of incident edges
        return incidentEdges.iterator();
    }

    // getting the edge connecting the given nodes
    // throws a GraphException if there is no edge connecting the given nodes, or if u or v do not exist
    public Edge getEdge(Node node1, Node node2) throws GraphException {
        // if first node not in graph
        if (node1.getId() < 0 || node1.getId() >= numNodes) {throw new GraphException("First node is not in the graph");

        // if second node not in graph
        } else if (node2.getId() < 0 || node2.getId() >= numNodes) { throw new GraphException("Second node is not in the graph");

        // if edge does not exist
        } else if (adjMatrix.get(node1.getId()).get(node2.getId()) == 0) {throw new GraphException("Edge does not exist");

            // finding and returning the edge
        } else {
            for (int i = 0; i < numEdges; i++) {
                if (edges[i].firstNode().equals(node1) && edges[i].secondNode().equals(node2)) {
                    return edges[i];
                }
            }
        }
        return null;
    }

    // if the given nodes are adjacent
    // returns true if they are adjacent, false if they are not
    // throws a GraphException if either node does not exist
    public boolean areAdjacent(Node node1, Node node2) throws GraphException {

        // if first node not in graph
        if (node1.getId() < 0 || node1.getId() >= numNodes) { throw new GraphException("Node1 is not in the graph");

        // if second node not in graph
        } else if (node2.getId() < 0 || node2.getId() >= numNodes) { throw new GraphException("Node2 is not in the graph");
        }

        // checking if the given nodes are adjacent by consulting the adjacency matrix
        return (adjMatrix.get(node1.getId()).get(node2.getId()) != 0);
    }

}