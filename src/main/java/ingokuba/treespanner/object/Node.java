package ingokuba.treespanner.object;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Node
{

    private String     name;
    private Integer    id;
    private Node       root         = this;
    private int        cost         = 0;
    private Node       nextHop      = this;
    private int        messageCount = 0;
    private List<Path> paths        = new ArrayList<>();

    public Node()
    {
    }

    public Node(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    /**
     * Offer path to root with a specific cost.
     * 
     * @param node The offering node
     */
    public void offer(Node node, Node root, int cost)
    {
        messageCount++;
        if (root.getId() < this.root.getId()) {
            this.root = root;
            this.cost = cost;
            this.nextHop = node;
            broadcast();
        }
        else if (root.equals(this.root) && cost < this.cost) {
            this.cost = cost;
            this.nextHop = node;
            broadcast();
        }
    }

    /**
     * Broadcast offerings to all linked nodes.
     */
    public void broadcast()
    {
        for (Path path : paths) {
            path.getNode().offer(this, root, cost + path.getCost());
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        return name.equals(((Node)o).getName());
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
