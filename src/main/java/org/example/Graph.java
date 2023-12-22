package org.example;

import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> vertices;

    public Graph() {
        vertices = new HashMap<>();
    }

    public void addVertex(int vertex) {
        vertices.put(vertex, new ArrayList<>());
    }

    public boolean hasVertex(int vertex) {
        return vertices.containsKey(vertex);
    }

    public void addEdge(int vertex1, int vertex2) {
        if (vertices.containsKey(vertex1) && vertices.containsKey(vertex2)) {
            vertices.get(vertex1).add(vertex2);
            vertices.get(vertex2).add(vertex1);
        }
    }

    public List<Integer> getNeighbors(int vertex) {
        if (vertices.containsKey(vertex)) {
            return vertices.get(vertex);
        }
        return Collections.emptyList();
    }

    public boolean hasEdge(int vertex1, int vertex2) {
        if (vertices.containsKey(vertex1) && vertices.containsKey(vertex2)) {
            return vertices.get(vertex1).contains(vertex2);
        }
        return false;
    }

    public static Graph createAsGameField() {
        Graph result = new Graph();
        for(int i = 1; i < 101; i++) {
            result.addVertex(i);
        }
        for (int i = 1; i < 100; i++) {
            if (i < 91) {
                if (i % 10 != 0 ) {
                    result.addEdge(i, i+1);
                    result.addEdge(i, i+10);
                    result.addEdge(i, i+11);
                    if (i % 10 != 1) {
                        result.addEdge(i, i + 9);
                    }
                } else {
                    result.addEdge(i, i+10);
                    result.addEdge(i, i+9);
                }
            } else {
                result.addEdge(i, i+1);
            }
        }
        return result;
    }

    //метод получения соседних клеток корабля
    public Set<Integer> getShipAdjectiveCells(List<Integer> positions) {
        Set<Integer> adjectiveCells = new HashSet<>();
        for(Integer pos : positions) {
            adjectiveCells.addAll(this.getNeighbors(pos));
        }
        return adjectiveCells;
    }
}