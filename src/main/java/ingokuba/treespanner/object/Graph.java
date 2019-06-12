package ingokuba.treespanner.object;

import java.util.ArrayList;
import java.util.List;

public class Graph
{

    private List<Node> nodes;
    private List<Link> links;
    private Node       root;

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

    public void addLink(Link link)
    {
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
    }

    public List<Node> getNodes()
    {
        return nodes;
    }

    public void setNodes(List<Node> nodes)
    {
        this.nodes = nodes;
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
