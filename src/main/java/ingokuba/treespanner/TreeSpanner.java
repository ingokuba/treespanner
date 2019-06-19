package ingokuba.treespanner;

import ingokuba.treespanner.object.Graph;
import ingokuba.treespanner.object.Node;
import ingokuba.treespanner.object.SpanningTree;

public class TreeSpanner
{

    private SpanningTree spanningTree;

    public TreeSpanner(Graph graph)
        throws TreespannerException
    {
        graph.check();
        this.spanningTree = new SpanningTree(graph);
    }

    /**
     * Simulate root finding from all nodes to the root.
     * 
     * @return The spanning tree object.
     */
    public SpanningTree getSpanningTree(int minPDU)
    {
        boolean reached = false;
        while (!reached) {
            for (Node node : spanningTree.getGraph().getNodes()) {
                reached = node.getMessageCount() > minPDU;
                node.broadcast();
            }
        }
        return spanningTree;
    }
}
