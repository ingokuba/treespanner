package ingokuba.treespanner.object;

import java.util.List;

import lombok.Data;

@Data
public class SpanningTree
{

    private Node       root;
    private List<Link> links;

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Root: " + root.getName() + "; ");
        for (Link link : links) {
            builder.append(link.getNode(0) + " - " + link.getNode(1) + "; ");
        }
        return builder.toString();
    }
}
