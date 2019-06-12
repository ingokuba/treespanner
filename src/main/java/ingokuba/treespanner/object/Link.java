package ingokuba.treespanner.object;

public class Link
{

    private String[] nodes;
    private Integer  cost;

    public String[] getNodes()
    {
        return nodes;
    }

    public void setNodes(String[] nodes)
    {
        if (nodes.length != 2) {
            throw new IllegalArgumentException("Link has to contain two node names.");
        }
        this.nodes = nodes;
    }

    public Integer getCost()
    {
        return cost;
    }

    public void setCost(Integer cost)
    {
        this.cost = cost;
    }

    public String getNode(int index)
    {
        if (index != 0 && index != 1) {
            throw new IllegalArgumentException("Index has to be 0 or 1.");
        }
        return nodes[index];
    }
}
