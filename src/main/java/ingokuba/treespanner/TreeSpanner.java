package ingokuba.treespanner;

import java.util.ArrayList;
import java.util.List;

import ingokuba.treespanner.object.Graph;
import ingokuba.treespanner.object.Link;
import ingokuba.treespanner.object.Node;
import ingokuba.treespanner.object.SpanningTree;

public class TreeSpanner
{

    private Graph graph;

    public TreeSpanner(Graph graph)
    {
        this.graph = graph;
        check();
    }

    /**
     * Simulate root finding from all nodes to the root.
     * 
     * @return The spanning tree object.
     */
    public SpanningTree getSpanningTree()
    {
        for (Node node : graph.getNodes()) {
            findRoot(node);
        }
        return null;
    }

    private void findRoot(Node from)
    {

    }

    /**
     * Run all checks on the graph.
     * 
     * @return
     */
    private void check()
    {
        List<Integer> nodeIds = new ArrayList<>();
        List<String> nodeNames = new ArrayList<>();
        for (Node node : graph.getNodes()) {
            // check node id
            if (nodeIds.contains(node.getId())) {
                throw new IllegalArgumentException("Node id '" + node.getId() + "' is not unique.");
            }
            nodeIds.add(node.getId());
            // check node name
            if (nodeNames.contains(node.getName())) {
                throw new IllegalArgumentException("Node name '" + node.getName() + "' is not unique.");
            }
            nodeNames.add(node.getName());
        }
        for (Link link : graph.getLinks()) {
            String nodeName0 = link.getNode(0);
            String nodeName1 = link.getNode(1);
            if (nodeName0.equals(nodeName1)) {
                throw new IllegalArgumentException("Node " + nodeName0 + " references itself.");
            }
            getNode(nodeName0);
            getNode(nodeName1);
        }
        // check if all nodes are connected
    }

    private Node getNode(String name)
    {
        Node node = graph.getNode(name);
        if (node == null) {
            throw new IllegalArgumentException("Node with name '" + name + "' doesn't exist.");
        }
        return node;
    }
}
