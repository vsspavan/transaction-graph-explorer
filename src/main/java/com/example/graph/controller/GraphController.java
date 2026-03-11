package com.example.graph.controller;

import com.example.graph.dto.ApiResponse;
import com.example.graph.dto.GraphNodeResponse;
import com.example.graph.service.GraphService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/graph")
public class GraphController {

    private final GraphService graphService;

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/nodes/{id}")
    public ApiResponse<Object> getNode(
            @PathVariable String id,
            @RequestParam(defaultValue = "1") int maxDepth) {

        GraphNodeResponse nodeDetails = graphService.getNodeDetails(id);

        Object tree = null;

        if (maxDepth > 1) {
            tree = graphService.buildChildrenTree(id, maxDepth);
        }

        Map<String,Object> data = new HashMap<>();
        data.put("nodeDetails", nodeDetails);
        data.put("childrenTree", tree);

        ApiResponse<Object> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setMessage("Data fetched Successfully");

        return response;
    }
}