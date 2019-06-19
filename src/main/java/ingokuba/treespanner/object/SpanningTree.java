package ingokuba.treespanner.object;

import java.util.List;

import lombok.Data;

@Data
public class SpanningTree
{

    private Graph graph;

    public SpanningTree(Graph graph)
    {
        this.graph = graph;
        init(graph);
    }

    private void init(Graph graph)
    {
        for (Link link : graph.getLinks()) {
            Node node0 = graph.getNode(link.getNode(0));
            Node node1 = graph.getNode(link.getNode(1));
            node0.getPaths().add(new Path(node1, link.getCost()));
            node1.getPaths().add(new Path(node0, link.getCost()));
        }
    }

    @Override
    public String toString()
    {
        List<Node> nodes = graph.getNodes();
        StringBuilder builder = new StringBuilder("Spanning-Tree of " + graph.getName() + "{\n");
        // find root
        builder.append("     Root: " + nodes.get(0).getRoot().getName() + ";\n");
        for (Node node : nodes) {
            builder.append("     " + node.getName() + " - " + node.getNextHop().getName() + ";\n");
        }
        return builder.append("}\n").toString();
    }
}
