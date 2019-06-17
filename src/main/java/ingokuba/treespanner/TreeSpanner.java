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
        throws TreespannerException
    {
        this.graph = graph;
        check();
    }

    /**
     * Simulate root finding from all nodes to the root.
     * 
     * @return The spanning tree object.
     */
    public SpanningTree getSpanningTree(int minPDU)
    {
        return null;
    }

    /**
     * Run all checks on the graph.
     */
    private void check()
        throws TreespannerException
    {
        List<Integer> nodeIds = new ArrayList<>();
        List<String> nodeNames = new ArrayList<>();
        for (Node node : graph.getNodes()) {
            // check node id
            if (nodeIds.contains(node.getId())) {
                throw new TreespannerException("Node id '%d' is not unique.", node.getId());
            }
            nodeIds.add(node.getId());
            // check node name
            if (nodeNames.contains(node.getName())) {
                throw new TreespannerException("Node name '%s' is not unique.", node.getName());
            }
            nodeNames.add(node.getName());
        }
        for (Link link : graph.getLinks()) {
            String nodeName0 = link.getNode(0);
            String nodeName1 = link.getNode(1);
            if (nodeName0.equals(nodeName1)) {
                throw new TreespannerException("Node '%s' references itself.", nodeName0);
            }
            getNode(nodeName0);
            getNode(nodeName1);
        }
        // check if all nodes are connected
    }

    private Node getNode(String name)
        throws TreespannerException
    {
        Node node = graph.getNode(name);
        if (node == null) {
            throw new TreespannerException("Node with name '%s' doesn't exist.", name);
        }
        return node;
    }
}
