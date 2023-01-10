// Arjun Sharma (251240847)
// Map class, used to make a graph and find a solution to the map problem

import java.io.*;
import java.util.Iterator;
import java.util.Stack;

public class MyMap{
    private Graph roadMap; // The graph representing the road map
    private Node startingNode; // The starting node
    private Node destinationNode; // The destination node
    private int maxPrivateRoads; // The maximum number of private roads
    private int maxConstructionRoads; // The maximum number of construction roads
    Stack<Node> path = new Stack<>(); // Stack to store the path
    private int privateUsed = 0;
    private int constructionUsed = 0;
    private Stack<Edge> edgePath = new Stack<>();

    // receives a string with the name of the file containing the map and creates a graph
    public MyMap(String inputFile) throws IOException, GraphException, MapException {
        // set instance variables and make graph here
        try {
            File file = new File(inputFile);


            // reading the file
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String line; // line of the file
            int counter = 0; // counter for the line number
            int width = 0; // width of the graph
            int length = 0; // length of the graph
            int destination = 0; // destination node id
            int start = 0; // starting node id
            int horizontalNode = 0; // horizontal node id for the graph
            int verticalNode = 0;  // vertical node id for the graph

            // keep reading the lines until the end of the file
            while ((line = bufferedReader.readLine()) != null) {
                String[] characters = line.split("");

                // if line is the 2nd line, these it is the starting node id
                if (counter == 1) {
                    start = Integer.parseInt(line);

                    // if line is the third line, then it is the destination node id
                } else if (counter == 2) {
                    destination = Integer.parseInt(line);

                    // if line is 4th line, it is the width of the graph
                } else if (counter == 3) {
                    width = Integer.parseInt(line);

                    // if line is 5th line, it is the length of the graph
                } else if (counter == 4) {
                    length = Integer.parseInt(line);

                    // with length and width information, we can create an empty graph
                    roadMap = new Graph(width * length);
                    destinationNode = roadMap.getNode(destination);
                    startingNode = roadMap.getNode(start);

                    // if line is 6th line, it is the max private roads
                } else if (counter == 5) {
                    maxPrivateRoads = Integer.parseInt(line);

                    // iif line is 7th line then it is the max construction roads
                } else if (counter == 6) {
                    maxConstructionRoads = Integer.parseInt(line);
                }

                // after the 8th line is info for the graph
                else if (counter > 6) {

                    // if the row is even, then we are making the edges horizontally
                    if (isEven(counter - 7)) {

                        // going through the line character by character
                        for (int i = 0; i < characters.length; i++) {

                            // every even character is a node
                            if (isEven(i)) {

                                // testing if we are at the end of the line
                                try { String test = characters[i + 1];
                                } catch (IndexOutOfBoundsException e) {
                                    horizontalNode++;
                                    break;
                                }

                                // letter to the right of every even letter denotes the edge type
                                // therefore, we create a edge starting with the current node and ending with the next node

                                // public road
                                if (characters[i + 1].compareTo("P") == 0) {
                                    roadMap.addEdge(roadMap.getNode(horizontalNode),
                                            roadMap.getNode(horizontalNode + 1), "public");

                                    // private road
                                } else if (characters[i + 1].compareTo("V") == 0) {
                                    roadMap.addEdge(roadMap.getNode(horizontalNode),
                                            roadMap.getNode(horizontalNode + 1), "private");

                                    // construction road
                                } else if (characters[i + 1].compareTo("C") == 0) {
                                    roadMap.addEdge(roadMap.getNode(horizontalNode),
                                            roadMap.getNode(horizontalNode + 1), "construction");
                                }

                                // incrementing the horizontal node
                                horizontalNode++;
                            }
                        }

                        // if the row is odd then we are making vertical edges
                    } else {


                        int nodeCounter = 0; // used to get node below the current vertical node
                        for (int i = 0; i < characters.length; i++) {

                            // every even character is a edge we need to make
                            if (isEven(i) && counter - 7 != 0) {

                                // public road
                                if (characters[i].compareTo("P") == 0) {
                                    roadMap.addEdge(roadMap.getNode(verticalNode),
                                            roadMap.getNode(horizontalNode + nodeCounter), "public");

                                    // private road
                                } else if (characters[i].compareTo("V") == 0) {
                                    roadMap.addEdge(roadMap.getNode(verticalNode),
                                            roadMap.getNode(horizontalNode + nodeCounter), "private");

                                    // construction road
                                } else if (characters[i].compareTo("C") == 0) {
                                    roadMap.addEdge(roadMap.getNode(verticalNode),
                                            roadMap.getNode(horizontalNode + nodeCounter), "construction");
                                }

                                // incrementing variables in for loop
                                nodeCounter++;
                                verticalNode++;
                            }
                        }
                    }
                }
                // incrementing the counter for the line number
                counter++;
            }
        } catch (FileNotFoundException e) {
            throw new MapException("File Not Found");
        }
    }

    // returns a graph of the map
    Graph getGraph() { return roadMap; }

    // gets the starting node of the map
    int getStartingNode() { return startingNode.getId(); }

    // gets the destination we are looking for
    int getDestinationNode() {return destinationNode.getId();}

    // the maximum number of private roads we can use
    int maxPrivateRoads() {return maxPrivateRoads;}

    // the maximum number of construction roads we can use
    int maxConstructionRoads() {return maxConstructionRoads;}

    // finding a path from the starting node to the destination node, whilst using the max number of private and construction roads
    Iterator findPath(int start, int dest, int maxPrivate, int maxConstruction) throws GraphException {
        Node startingNode = roadMap.getNode(start); // starting node

        // marking the starting node as visited and pushing it onto the stack
        startingNode.markNode(true);
        path.push(startingNode);

        // returning the iterator for the path
        if (start == dest) return path.iterator();


        Edge edge;
        Iterator incidentEdges = roadMap.incidentEdges(startingNode);

        // looking through all edges on the current node
        while (incidentEdges.hasNext()) {

            // getting the edge object
            edge = (Edge) incidentEdges.next();

            // if the path has not been looked through
            if (!edge.secondNode().getMark()) {

                // seeing what type of edge we have and decrementing the value once we know where we are moving
                // decrementing construction
                if (edge.getType().compareTo("construction") == 0) {
                    // if we are allowed to use a construction road. If not, then go to another edge
                    if (constructionUsed != maxConstruction) constructionUsed++;
                    else continue;

                }

                // decrementing private
                if (edge.getType().compareTo("private") == 0) {
                    // if we are allowed to use a private road. If not, then go to another edge
                    if (privateUsed != maxPrivate) privateUsed++;
                    else continue;

                }

                // looking through graph and adding more to the stack
                edgePath.push(edge);
                if (findPath(edge.secondNode().getId(), dest, maxPrivate, maxConstruction) != null)
                    return path.iterator();
            }

            // exact same except allowing us to move up and left

            else if (!edge.firstNode().getMark()) {
                // seeing what type of edge we have and decrementing the value once we know where we are moving
                // decrementing construction
                if (edge.getType().compareTo("construction") == 0) {
                    // if we are allowed to use a construction road. If not, then go to another edge
                    if (constructionUsed != maxConstruction) constructionUsed++;
                    else continue;

                }

                // decrementing private
                if (edge.getType().compareTo("private") == 0) {
                    // if we are allowed to use a private road. If not, then go to another edge
                    if (privateUsed != maxPrivate) privateUsed++;
                    else continue;

                }

                // looking through graph and adding more to the stack
                edgePath.push(edge);
                if (findPath(edge.firstNode().getId(), dest, maxPrivate, maxConstruction) != null)
                    return path.iterator();
            }
        }

        // checking if when we go back in the path to see if we backtracked thru a private or construction road
        if (!edgePath.isEmpty()) {
            if (edgePath.peek().getType().compareTo("construction") == 0) constructionUsed--;
            if (edgePath.peek().getType().compareTo("private") == 0) privateUsed--;
            edgePath.pop();
        }

        // un marks the node when we back track
        path.peek().markNode(false);
        path.pop();

        return null;
    }

    // helper method, returns true if the number is even false if odd
    private boolean isEven(int num) {
        return num % 2 == 0;
    }

}