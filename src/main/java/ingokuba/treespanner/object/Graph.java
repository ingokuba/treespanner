package ingokuba.treespanner.object;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Graph
{

    private List<Node> nodes;
    private List<Link> links;
    private Node       root;

    public void addLink(Link link)
    {
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
    }

    /**
     * Find a node with the given id.
     */
    public Node getNode(Integer id)
    {
        for (Node node : nodes) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Find a node with the given name.
     */
    public Node getNode(String name)
    {
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public void addNode(Node node)
    {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        nodes.add(node);
    }
}
