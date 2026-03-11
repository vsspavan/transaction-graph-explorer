package com.example.graph.loader;

import com.example.graph.model.GraphData;
import com.example.graph.model.GraphNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

@Component
@Getter
public class GraphDataLoader {

    private Map<String, GraphNode> nodeById = new HashMap<>();
    private Map<String, List<GraphNode>> childrenByParentId = new HashMap<>();

    @PostConstruct
    public void loadData() {

        try {

            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream =
                    new ClassPathResource("transactions-graph-nodes.json").getInputStream();

            GraphData graphData = mapper.readValue(inputStream, GraphData.class);

            for (GraphNode node : graphData.getNodes()) {

                nodeById.put(node.getId(), node);

                if (node.getParentId() != null) {

                    childrenByParentId
                            .computeIfAbsent(node.getParentId(), k -> new ArrayList<>())
                            .add(node);
                }
            }

            System.out.println("Nodes loaded: " + nodeById.size());

        } catch (Exception e) {

            throw new RuntimeException("Failed to load graph data", e);
        }
    }
}