package com.example.graph.exception;

public class Notfoundexception extends RuntimeException {

    public Notfoundexception(String nodeId) {
        super("Graph node " + nodeId + " does not exist");
    }
}