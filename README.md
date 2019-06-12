# Tree Spanner [![CircleCI](https://circleci.com/gh/ingokuba/treespanner.svg?style=svg)](https://circleci.com/gh/ingokuba/treespanner)

Labor Kommunikationsinformatik: Simulation eines Spanning Tree Protokolls

### Executing

`java -jar target/treespanner.jar arg0 arg1`

Arguments:
- arg0: File path for the graph
- arg1: Minimum PDUs: Amount of minimum message count every node should reach

Example:
`java -jar target/treespanner.jar Example.json 10`

### Supported file types

- Default: file should have no ending or end with `.custom`
- Json: file should end with `.json`
