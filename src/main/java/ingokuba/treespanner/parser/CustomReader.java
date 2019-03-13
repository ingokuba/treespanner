package ingokuba.treespanner.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ingokuba.treespanner.object.Graph;
import ingokuba.treespanner.object.Link;
import ingokuba.treespanner.object.Node;

public class CustomReader implements GraphReader {

	private static final Logger LOGGER = Logger.getLogger(CustomReader.class.getName());

	@Override
	public Graph read(Path path) {
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			Graph graph = new Graph();
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.matches("\\s*[A-Z][a-zA-Z]*\\s*=\\s*[0-9]*\\s*;\\s*")) {
					// node
					graph.addNode(getNode(line));
				} else if (line.matches("\\s*[A-Z][a-zA-Z]*\\s*-\\s*[A-Z][a-zA-Z]*\\s*:\\s*[0-9]*\\s*;\\s*")) {
					// link
					graph.addLink(getLink(line));
				}
			}
			return graph;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Could not read from file '" + path + "'", e);
			return null;
		}
	}

	/**
	 * Node as a string in the format:
	 * 
	 * <pre>
	 * {name} = {id};
	 * </pre>
	 */
	private Node getNode(String string) {
		Node node = new Node();
		// name
		Pattern namePattern = Pattern.compile("[A-Z][a-zA-Z]*");
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
	private Link getLink(String string) {
		Link link = new Link();
		// nodes
		Pattern namePattern = Pattern.compile("[A-Z][a-zA-Z]*");
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
