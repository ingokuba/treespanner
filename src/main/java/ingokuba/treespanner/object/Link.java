package ingokuba.treespanner.object;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Link
{

    private String[] nodes;
    private Integer  cost;

    public void setNodes(String[] nodes)
    {
        if (nodes.length != 2) {
            throw new IllegalArgumentException("Link has to contain two node names.");
        }
        this.nodes = nodes;
    }

    public String getNode(int index)
    {
        if (index != 0 && index != 1) {
            throw new IllegalArgumentException("Index has to be 0 or 1.");
        }
        return nodes[index];
    }

    public Link setNodes(Node node0, Node node1)
    {
        String[] nodeCopy = {node0.getName(), node1.getName()};
        this.nodes = nodeCopy;
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || (this.getClass() != o.getClass())) {
            return false;
        }
        Link other = (Link)o;
        if (getNode(0).equals(other.getNode(0)) && getNode(1).equals(other.getNode(1))) {
            return true;
        }
        return getNode(0).equals(other.getNode(1)) && getNode(1).equals(other.getNode(0));
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
