package ingokuba.treespanner.reader;

import static ingokuba.treespanner.Config.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ingokuba.treespanner.Config;
import ingokuba.treespanner.TreespannerException;
import ingokuba.treespanner.object.Graph;
import ingokuba.treespanner.object.Link;
import ingokuba.treespanner.object.Node;

public class CustomReader
    implements GraphReader
{

    private static final String INVALID_LINE_SEQUENCE = "Invalid line sequence.";
    private static final String GRAPH_NAME_REGEX      = "[a-zA-Z][a-zA-Z0-9]*";
    private static final String NODE_NAME_REGEX       = "[A-Z][a-zA-Z]*";

    @Override
    public Graph read(Path path)
        throws TreespannerException
    {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            final Integer MAX_ITEMS = config().getProperty(Config.MAX_ITEMS, Integer.class);
            Graph graph = new Graph();
            boolean started = false;
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                if (MAX_ITEMS != null && i > MAX_ITEMS) {
                    throw new TreespannerException("Line count is greater than %d", MAX_ITEMS);
                }
                if (line.matches("\\s*Graph\\s*" + GRAPH_NAME_REGEX + "\\s*\\{\\s*")) {
                    if (started) {
                        throw new TreespannerException(INVALID_LINE_SEQUENCE);
                    }
                    // graph name
                    graph.setName(getGraphName(line));
                    started = true;
                }
                if (line.matches("\\s*" + NODE_NAME_REGEX + "\\s*=\\s*[0-9]*\\s*;\\s*")) {
                    if (!started) {
                        throw new TreespannerException(INVALID_LINE_SEQUENCE);
                    }
                    // node
                    graph.addNode(getNode(line));
                }
                if (line.matches("\\s*" + NODE_NAME_REGEX + "\\s*-\\s*" + NODE_NAME_REGEX + "\\s*:\\s*[0-9]*\\s*;\\s*")) {
                    if (!started) {
                        throw new TreespannerException(INVALID_LINE_SEQUENCE);
                    }
                    // link
                    graph.addLink(getLink(line));
                }
                if (line.matches("\\s*\\}\\s*")) {
                    if (!started) {
                        throw new TreespannerException(INVALID_LINE_SEQUENCE);
                    }
                    break;
                }
            }
            return graph;
        } catch (IOException e) {
            throw new TreespannerException("Could not read from file '%s'", path);
        }
    }

    /**
     * Graph name in a line like:
     * 
     * <pre>
     * Graph {name} {
     * </pre>
     */
    private String getGraphName(String string)
    {
        Pattern namePattern = Pattern.compile(GRAPH_NAME_REGEX);
        Matcher nameMatcher = namePattern.matcher(string);
        return nameMatcher.find() ? nameMatcher.group(0) : null;
    }

    /**
     * Node as a string in the format:
     * 
     * <pre>
     * {name} = {id};
     * </pre>
     */
    private Node getNode(String string)
    {
        Node node = new Node();
        // name
        Pattern namePattern = Pattern.compile(NODE_NAME_REGEX);
        Matcher nameMatcher = namePattern.matcher(string);
        if (nameMatcher.find()) {
            String name = nameMatcher.group(0);
            node.setName(name);
        }
        // id
        Pattern idPattern = Pattern.compile("[0-9]{1,}");
        Matcher idMatcher = idPattern.matcher(string);
        if (idMatcher.find()) {
            String id = idMatcher.group(0);
            node.setId(Integer.valueOf(id));
        }
        return node;
    }

    /**
     * Link as a string in the format:
     * 
     * <pre>
     * {node1.name} - {node2.name} : {cost};
     * </pre>
     */
    private Link getLink(String string)
    {
        Link link = new Link();
        // nodes
        Pattern namePattern = Pattern.compile(NODE_NAME_REGEX);
        Matcher nameMatcher = namePattern.matcher(string);
        List<String> nodes = new ArrayList<>();
        while (nameMatcher.find()) {
            nodes.add(nameMatcher.group(0));
        }
        link.setNodes(nodes.toArray(new String[0]));
        // cost
        Pattern costPattern = Pattern.compile("[0-9]{1,}");
        Matcher costMatcher = costPattern.matcher(string);
        if (costMatcher.find()) {
            String cost = costMatcher.group(0);
            link.setCost(Integer.valueOf(cost));
        }
        return link;
    }
}
