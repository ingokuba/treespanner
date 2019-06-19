# Tree Spanner [![CircleCI](https://circleci.com/gh/ingokuba/treespanner.svg?style=svg)](https://circleci.com/gh/ingokuba/treespanner)

:deciduous_tree: Simulation of a spanning tree protocol

### Executing

`java -jar target/treespanner.jar arg0 arg1`

Arguments:
- arg0: File path for the graph
- arg1: Minimum PDUs: Amount of minimum message count every node should reach

Example:
`java -jar target/treespanner.jar Example.json 10`

### Supported file types

- Default: file should have no ending or end with `.txt` or `.custom`
- Json: file should end with `.json`

### Configuration

in `Treespanner.properties`:
- `MAX_ITEMS`: Maximum amount of lines in the input file
- `MAX_IDENT`: Maxmimum length for identifiers (graph and node names)
- `MAX_COST`: Value range of the edge weights (costs): 0 ... `MAX_COST`
- `MAX_NODE_ID`: Value range of the node weights (Node ID): 1 ... `MAX_NODE_ID`