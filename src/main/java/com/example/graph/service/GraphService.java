package com.example.graph.service;

import com.example.graph.dto.GraphNodeResponse;
import com.example.graph.dto.GraphNodeTreeDTO;
import com.example.graph.exception.Notfoundexception;
import com.example.graph.loader.GraphDataLoader;
import com.example.graph.model.GraphNode;
import com.example.graph.model.NodeTransaction;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {

    private final GraphDataLoader dataLoader;

    public GraphService(GraphDataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public GraphNodeResponse getNodeDetails(String nodeId) {

        GraphNode node = dataLoader.getNodeById().get(nodeId);

        if (node == null) {
            throw new Notfoundexception(nodeId);
        }

        int level = calculateLevel(node);

        List<GraphNode> parentChain = getParentChain(node);

        List<GraphNode> children =
                dataLoader.getChildrenByParentId().getOrDefault(nodeId, new ArrayList<>());

        boolean isRoot = node.getParentId() == null ||
                !dataLoader.getNodeById().containsKey(node.getParentId());

        boolean isLeaf = children.isEmpty();

        List<NodeTransaction> nextLevelTransactions = new ArrayList<>();

        for (GraphNode child : children) {
            if (child.getTransactions() != null) {
                nextLevelTransactions.addAll(child.getTransactions());
            }
        }

        GraphNodeResponse response = new GraphNodeResponse();

        response.setNode(node);
        response.setLevel(level);
        response.setParentChain(parentChain);
        response.setChildren(children);
        response.setTransactions(node.getTransactions());
        response.setNextLevelTransactions(nextLevelTransactions);
        response.setRoot(isRoot);
        response.setLeaf(isLeaf);

        return response;
    }

    private int calculateLevel(GraphNode node) {

        int level = 0;
        String parentId = node.getParentId();

        while (parentId != null && dataLoader.getNodeById().containsKey(parentId)) {
            level++;
            parentId = dataLoader.getNodeById().get(parentId).getParentId();
        }

        return level;
    }

    private List<GraphNode> getParentChain(GraphNode node) {

        List<GraphNode> chain = new ArrayList<>();

        String parentId = node.getParentId();

        while (parentId != null && dataLoader.getNodeById().containsKey(parentId)) {

            GraphNode parent = dataLoader.getNodeById().get(parentId);

            chain.add(parent);

            parentId = parent.getParentId();
        }

        Collections.reverse(chain);

        return chain;
    }

    public GraphNodeTreeDTO buildChildrenTree(String nodeId, int depth) {

        if (depth <= 0) {
            return null;
        }

        GraphNode node = dataLoader.getNodeById().get(nodeId);

        if (node == null) {
            return null;
        }

        GraphNodeTreeDTO dto = new GraphNodeTreeDTO();

        dto.setId(node.getId());
        dto.setName(node.getName());
        dto.setAccountNumber(node.getAccountNumber());

        List<GraphNodeTreeDTO> childrenDTO = new ArrayList<>();

        List<GraphNode> children =
                dataLoader.getChildrenByParentId().getOrDefault(nodeId, new ArrayList<>());

        for (GraphNode child : children) {

            GraphNodeTreeDTO childTree =
                    buildChildrenTree(child.getId(), depth - 1);

            if (childTree != null) {
                childrenDTO.add(childTree);
            }
        }

        dto.setChildren(childrenDTO);

        return dto;
    }
}