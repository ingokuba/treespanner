package ingokuba.treespanner.object;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import ingokuba.treespanner.TreespannerException;

public class GraphTest
{

    @Test
    public void should_fail_check_on_missing_graph_name()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node nodeB = new Node().setId(2).setName("B");
        Graph graph = new Graph()
            .addNode(nodeA)
            .addNode(nodeB)
            .addLink(new Link().setCost(10).setNodes(nodeA, nodeB));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("Graph name is missing"));
        }
    }

    @Test
    public void should_fail_check_on_invalid_graph_name()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node nodeB = new Node().setId(2).setName("B");
        Graph graph = new Graph().setName("1graph")
            .addNode(nodeA)
            .addNode(nodeB)
            .addLink(new Link().setCost(10).setNodes(nodeA, nodeB));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("invalid format"));
        }
    }

    @Test
    public void should_fail_check_on_graph_name_too_long()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node nodeB = new Node().setId(2).setName("B");
        Graph graph = new Graph().setName("Graph123456")
            .addNode(nodeA)
            .addNode(nodeB)
            .addLink(new Link().setCost(10).setNodes(nodeA, nodeB));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("too long"));
        }
    }

    @Test
    public void should_fail_check_on_missing_node_name()
    {
        Graph graph = new Graph().setName("test")
            .addNode(new Node().setId(1));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("Node name is missing"));
        }
    }

    @Test
    public void should_fail_check_on_invalid_node_name()
    {
        Graph graph = new Graph().setName("test")
            .addNode(new Node().setId(1).setName("Node_"));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("invalid format"));
        }
    }

    @Test
    public void should_fail_check_on_node_name_too_long()
    {
        Graph graph = new Graph().setName("test")
            .addNode(new Node().setId(1).setName("Node1234567"));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("too long"));
        }
    }

    @Test
    public void should_fail_check_for_duplicate_node_name()
    {
        String duplicate = "Alabaster";
        Graph graph = new Graph().setName("test")
            .addNode(new Node().setId(1).setName(duplicate))
            .addNode(new Node().setId(2).setName(duplicate));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("name '" + duplicate + "' is not unique"));
        }
    }

    @Test
    public void should_fail_check_for_duplicate_root_id()
    {
        Graph graph = new Graph().setName("test")
            .addNode(new Node().setId(1).setName("A"))
            .addNode(new Node().setId(1).setName("B"));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("id '1' is not unique"));
        }
    }

    @Test
    public void should_succeed_check_for_duplicate_node_id()
        throws TreespannerException
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node nodeB = new Node().setId(2).setName("B");
        Node nodeC = new Node().setId(2).setName("C");
        Graph graph = new Graph().setName("test")
            .addNode(nodeA)
            .addNode(nodeB)
            .addNode(nodeC)
            .addLink(new Link().setCost(5).setNodes(nodeA, nodeB))
            .addLink(new Link().setCost(6).setNodes(nodeA, nodeC));

        graph.check();
    }

    @Test
    public void should_fail_check_for_missing_node_id()
    {
        Graph graph = new Graph().setName("test")
            .addNode(new Node().setName("A"));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("is missing"));
        }
    }

    @Test
    public void should_fail_check_for_node_id_too_low()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node nodeB = new Node().setId(0).setName("B");
        Graph graph = new Graph().setName("test")
            .addNode(nodeA).addNode(nodeB)
            .addLink(new Link().setCost(10).setNodes(nodeA, nodeB));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("has to be in range"));
        }
    }

    @Test
    public void should_fail_check_for_node_id_too_high()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node nodeB = new Node().setId(11).setName("B");
        Graph graph = new Graph().setName("test")
            .addNode(nodeA).addNode(nodeB)
            .addLink(new Link().setCost(10).setNodes(nodeA, nodeB));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("has to be in range"));
        }
    }

    @Test
    public void should_fail_check_for_self_reference()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Graph graph = new Graph().setName("test")
            .addNode(nodeA)
            .addNode(new Node().setId(2).setName("B"))
            .addLink(new Link().setCost(10).setNodes(nodeA, nodeA));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("references itself"));
        }
    }

    @Test
    public void should_fail_check_for_invalid_link()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node invalid = new Node().setId(3).setName("X");

        Graph graph = new Graph().setName("test")
            .addNode(nodeA)
            .addNode(new Node().setId(2).setName("B"))
            .addLink(new Link().setCost(10).setNodes(nodeA, invalid));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("doesn't exist"));
        }
    }

    @Test
    public void should_fail_check_for_missing_link_cost()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node nodeB = new Node().setId(2).setName("B");
        Graph graph = new Graph().setName("test")
            .addNode(nodeA).addNode(nodeB)
            .addLink(new Link().setNodes(nodeA, nodeB));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("Cost of link is missing"));
        }
    }

    @Test
    public void should_fail_check_for_link_cost_too_low()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node nodeB = new Node().setId(2).setName("B");
        Graph graph = new Graph().setName("test")
            .addNode(nodeA).addNode(nodeB)
            .addLink(new Link().setCost(-1).setNodes(nodeA, nodeB));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("has to be in range"));
        }
    }

    @Test
    public void should_fail_check_for_link_cost_too_high()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node nodeB = new Node().setId(2).setName("B");
        Graph graph = new Graph().setName("test")
            .addNode(nodeA).addNode(nodeB)
            .addLink(new Link().setCost(11).setNodes(nodeA, nodeB));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("has to be in range"));
        }
    }

    @Test
    public void should_fail_check_for_duplicate_link()
    {
        Node nodeA = new Node().setId(1).setName("A");
        Node nodeB = new Node().setId(2).setName("B");
        Graph graph = new Graph().setName("test")
            .addNode(nodeA).addNode(nodeB)
            .addLink(new Link().setCost(7).setNodes(nodeA, nodeB))
            .addLink(new Link().setCost(5).setNodes(nodeB, nodeA));

        try {
            graph.check();
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("Duplicate link"));
        }
    }
}
