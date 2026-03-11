# Design Document

## Data Loading Strategy

The application loads graph data from a static JSON file:

```
src/main/resources/transactions-graph-nodes.json
```

During application startup, the `GraphDataLoader` component reads the file using Jackson and converts it into Java objects.

---

## In-Memory Data Structures

Two primary maps are used for efficient graph traversal.

### Node Lookup

```
Map<String, GraphNode> nodeById
```

Purpose:

* O(1) access to nodes by ID.

Example:

```
"N1" -> GraphNode
"N2" -> GraphNode
```

---

### Parent-Children Relationship

```
Map<String, List<GraphNode>> childrenByParentId
```

Purpose:

* Quickly retrieve direct children of any node.

Example:

```
"N1" -> [N2, N3]
```

---

## Level Calculation

Node level is calculated by traversing the parent chain.

Algorithm:

```
level = 0
while parent exists
    level++
```

Root nodes (parentId = null or missing parent) are considered **Level 0**.

---

## Parent Chain Construction

The parent chain is built by repeatedly following `parentId` until a root node is reached.

Example:

```
N1 -> N2 -> N4
```

Parent chain for N4:

```
[N1, N2]
```

---

## Children Retrieval

Children are fetched directly using:

```
childrenByParentId.get(nodeId)
```

This ensures constant-time lookup.

---

## Next-Level Transactions

Transactions belonging to direct children nodes are aggregated by iterating over child nodes and collecting their transactions.

---

## Depth-Limited Traversal (maxDepth)

The `maxDepth` parameter allows building a nested children tree.

A recursive traversal is used:

```
buildChildrenTree(nodeId, depth)
```

If depth reaches zero, traversal stops.

This prevents excessive graph traversal and large response payloads.

---

## Error Handling

A custom exception `NodeNotFoundException` is thrown when a node ID does not exist.

Global error handling is implemented using:

```
@RestControllerAdvice
```

This ensures consistent error responses.

---

## API Response Structure

All API responses use a standardized wrapper:

```
ApiResponse<T>
```

Structure:

```
{
  success,
  data,
  error,
  message
}
```

This ensures consistent API behavior for both success and error responses.

---

## Scalability Considerations

Potential improvements for large datasets:

* Persist nodes in a database
* Use caching (Redis)
* Precompute node levels
* Implement pagination for large trees

---

## Conclusion

The design prioritizes:

* Simplicity
* Efficient lookups
* Clean API design
* Extensibility for future enhancements
