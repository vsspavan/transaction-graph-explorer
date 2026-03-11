package com.example.graph.dto;

import com.example.graph.model.GraphNode;
import com.example.graph.model.NodeTransaction;
import lombok.Data;

import java.util.List;

@Data
public class GraphNodeResponse {

    private GraphNode node;

    private int level;

    private List<GraphNode> parentChain;

    private List<GraphNode> children;

    private List<NodeTransaction> transactions;

    private List<NodeTransaction> nextLevelTransactions;

    private boolean isRoot;

    private boolean isLeaf;
}