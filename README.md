# Transaction Graph Node Explorer

## Overview

This project implements a **Spring Boot REST API** that allows exploration of a hierarchical **transaction graph**.

Each **Graph Node** represents an entity (e.g., account/activity bucket) and contains one or more **transactions**.

The hierarchy is defined using `parentId`, allowing traversal of nodes in a graph/tree structure.

The API allows clients to:

* Fetch node details
* Determine the node’s level in the hierarchy
* Retrieve parent chain
* Retrieve direct children nodes
* Retrieve node transactions
* Retrieve next-level transactions
* Traverse children nodes up to a configurable depth (`maxDepth`)

---

## Tech Stack

* Java 17
* Spring Boot 3.x
* Maven
* Jackson (JSON parsing)
* Lombok
* REST APIs

---

## How to Run the Application

1. Clone the repository or download the project.

2. Navigate to the project folder.

3. Run the application:

```bash
mvn spring-boot:run
```

or run the main class:

```
TransactionGraphExplorerApplication.java
```

4. The server will start at:

```
http://localhost:8080
```

---

## API Endpoint

### Get Graph Node Details

```
GET /api/graph/nodes/{id}
```

Example:

```
http://localhost:8080/api/graph/nodes/N1
```

---

### Optional Parameter

```
maxDepth
```

Example:

```
GET /api/graph/nodes/N1?maxDepth=3
```

This returns a nested **children tree up to depth 3**.

---

## Example Response

```json
{
  "success": true,
  "data": {
    "node": {...},
    "level": 0,
    "children": [...],
    "transactions": [...]
  }
}
```

---

## Error Response

If the node does not exist:

```json
{
  "success": false,
  "error": "NODE_NOT_FOUND",
  "message": "Graph node N999 does not exist"
}
```

HTTP Status: **404**

---

## Project Structure

```
com.example.graph
 ├── controller
 │    GraphController
 │
 ├── service
 │    GraphService
 │
 ├── loader
 │    GraphDataLoader
 │
 ├── model
 │    GraphNode
 │    NodeTransaction
 │
 ├── dto
 │    GraphNodeResponse
 │    GraphNodeTreeDTO
 │
 ├── exception
 │    NodeNotFoundException
 │    GlobalExceptionHandler
 │
 └── response
      ApiResponse
```

---

## Features Implemented

* Static JSON dataset loading
* In-memory graph representation
* Node level calculation
* Parent chain retrieval
* Direct children retrieval
* Next-level transaction aggregation
* REST API endpoint
* Global exception handling
* Standard API response wrapper
* Depth-limited tree traversal (`maxDepth`)

---

## Future Improvements

* Cycle detection in graph
* Aggregated statistics per level
* Transaction filtering APIs
* Database persistence
