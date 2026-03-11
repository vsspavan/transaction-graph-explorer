package com.example.graph.dto;

import lombok.Data;
import java.util.List;

@Data
public class GraphNodeTreeDTO {

    private String id;
    private String name;
    private String accountNumber;

    private List<GraphNodeTreeDTO> children;
}