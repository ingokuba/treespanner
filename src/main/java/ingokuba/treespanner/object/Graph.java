package ingokuba.treespanner.object;

import static ingokuba.treespanner.Config.config;

import java.util.ArrayList;
import java.util.List;

import ingokuba.treespanner.Config;
import ingokuba.treespanner.TreespannerException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
public class Graph
{

    private String     name;
    private List<Node> nodes;
    private List<Link> links;
    private Node       root;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @FieldNameConstants.Exclude
    private Config     config;

    public Graph addLink(Link link)
    {
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
        return this;
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

    public Graph addNode(Node node)
    {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        nodes.add(node);
        return this;
    }

    /**
     * Run all checks on the graph.
     */
    public void check()
        throws TreespannerException
    {
        config = config();
        checkIdent(name, Graph.class.getSimpleName());
        List<Integer> nodeIds = new ArrayList<>();
        List<String> nodeNames = new ArrayList<>();
        for (Node node : nodes) {
            // check node id
            Integer id = node.getId();
            checkNodeId(node);
            nodeIds.add(id);
            // check node name
            String nodeName = node.getName();
            checkIdent(nodeName, Node.class.getSimpleName());
            if (nodeNames.contains(nodeName)) {
                throw new TreespannerException("Node name '%s' is not unique.", nodeName);
            }
            nodeNames.add(nodeName);
        }
        // the smallest node ID may occur exclusively in the graph and thus identifies the root node
        nodeIds.sort((Integer id1, Integer id2) -> id1.compareTo(id2));
        Integer rootId = nodeIds.get(0);
        int count = count(nodeIds, rootId);
        if (count > 1) {
            throw new TreespannerException("Root id '%d' is not unique.", rootId);
        }
        if (links == null) {
            throw new TreespannerException("Links are missing.");
        }
        List<Link> tempLinks = new ArrayList<>();
        for (Link link : links) {
            checkCost(link);
            String nodeName0 = link.getNode(0);
            String nodeName1 = link.getNode(1);
            // Edges that connect a node to itself are not allowed
            if (nodeName0.equals(nodeName1)) {
                throw new TreespannerException("Node '%s' references itself.", nodeName0);
            }
            findNode(nodeName0);
            findNode(nodeName1);
            // Each edge may only be defined once
            if (tempLinks.contains(link)) {
                throw new TreespannerException("Duplicate link: %1$s-%2$s=%3$d", link.getNode(0), link.getNode(1), link.getCost());
            }
            tempLinks.add(link);
        }
        // check if all nodes are connected
    }

    /**
     * Count amount of appearances of a value in a list.
     */
    private int count(List<? extends Object> list, Object value)
    {
        int count = 0;
        for (Object element : list) {
            if (element.equals(value)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Value range of the edge weights (costs): 0 ... MAX_COST
     */
    private void checkCost(Link link)
        throws TreespannerException
    {
        Integer cost = link.getCost();
        if (cost == null) {
            throw new TreespannerException("Cost of link is missing.");
        }
        final Integer MAX_COST = config.getProperty(Config.MAX_COST, Integer.class);
        if (cost < 0 || (MAX_COST != null && cost > MAX_COST)) {
            throw new TreespannerException("Cost between node '%1$s' and node '%2$s' has to be in range 0-%3$s.",
                    link.getNode(0), link.getNode(1),
                    MAX_COST == null ? "infinite" : MAX_COST);
        }
    }

    /**
     * Value range of the node weights (Node ID): 1 ... MAX_NODE_ID
     */
    private void checkNodeId(Node node)
        throws TreespannerException
    {
        Integer id = node.getId();
        if (id == null) {
            throw new TreespannerException("Id of node '%s' is missing.", node.getName());
        }
        final Integer MAX_NODE_ID = config.getProperty(Config.MAX_NODE_ID, Integer.class);
        if (id < 1 || (MAX_NODE_ID != null && id > MAX_NODE_ID)) {
            throw new TreespannerException("Id of node '%1$s' has to be in range 1-%2$s.",
                    node.getName(), MAX_NODE_ID == null ? "infinite" : MAX_NODE_ID);
        }
    }

    /**
     * The graph or node identifiers begin with a letter followed by alphanumeric characters up to
     * one over the preprocessor constant MAX_IDENT preset maximum length.
     */
    private void checkIdent(String ident, String type)
        throws TreespannerException
    {
        if (ident == null) {
            throw new TreespannerException(type + " name is missing.");
        }
        final Integer MAX_IDENT = config.getProperty(Config.MAX_IDENT, Integer.class);
        if (MAX_IDENT != null && ident.length() > MAX_IDENT) {
            throw new TreespannerException(type + " name '%1$s' is too long (max length: %2$d)", ident, MAX_IDENT);
        }
        if (!ident.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
            throw new TreespannerException(type + " name '%s' has invalid format.", ident);
        }
    }

    private Node findNode(String name)
        throws TreespannerException
    {
        Node node = getNode(name);
        if (node == null) {
            throw new TreespannerException("Node with name '%s' doesn't exist.", name);
        }
        return node;
    }
}
