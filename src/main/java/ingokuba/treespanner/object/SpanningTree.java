package ingokuba.treespanner.object;

import java.util.List;

public class SpanningTree
{

    private Node       root;
    private List<Link> links;

    public Node getRoot()
    {
        return root;
    }

    public void setRoot(Node root)
    {
        this.root = root;
    }

    public List<Link> getLinks()
    {
        return links;
    }

    public void setLinks(List<Link> links)
    {
        this.links = links;
    }

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
