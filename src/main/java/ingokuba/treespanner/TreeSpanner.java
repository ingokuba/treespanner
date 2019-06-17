package ingokuba.treespanner;

import ingokuba.treespanner.object.Graph;
import ingokuba.treespanner.object.SpanningTree;

public class TreeSpanner
{

    private Graph graph;

    public TreeSpanner(Graph graph)
        throws TreespannerException
    {
        graph.check();
        this.graph = graph;
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
}
