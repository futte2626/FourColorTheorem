package org.Bjornbak;

import java.awt.*;
import java.util.ArrayList;

public final class Graph {
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;

    public Graph() {
        vertices = new ArrayList<>(0);
        edges = new ArrayList<>(0);
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public void addEdge(Vertex v1, Vertex v2) {
        edges.add(new Edge(v1, v2));
    }

    public void printGraph() {
        for (Vertex v : vertices) {
            System.out.println("Vertex: " + v.name);
            for (Edge e : edges) {
                if(e.a.equals(v) || e.b.equals(v)) {
                    System.out.println("Edge: " + e.a.name + " --> " + e.b.name);
                }
            }
        }
    }

    public void printVertices(ArrayList<Vertex> v) {
        System.out.println("Vertices: ");
        for (Vertex v1 : v) {
            System.out.println(v1.name + ", ");
        }
    }

    public int getVertexDegree(Vertex v) {
        int degree = 0;
        for (Edge e : edges) {
            if(e.a.equals(v)) {
                degree++;
            }
        }
        return degree;
    }

    public ArrayList<Vertex> getNeighbors(Vertex v) {
        ArrayList<Vertex> neighbors = new ArrayList<>();
        for (Edge e : edges) {
            if(e.a.equals(v)) {
                neighbors.add(e.b);
            }
        }

        return neighbors;
    }

    public ArrayList<String> getNeighborName(Vertex v) {
        ArrayList<String> neighbors = new ArrayList<>();
        for (Edge e : edges) {
            if(e.a.equals(v)) {
                neighbors.add(e.b.name);
            }
        }

        return neighbors;
    }

    public Graph ColorGraph(int maxColor) {
        int highestDegree = 0;
        int higestDegreePos = 0;
        for (int i = 0; i < this.vertices.size(); i++) {
            if(getVertexDegree(this.vertices.get(i)) > highestDegree) {
                highestDegree = getVertexDegree(this.vertices.get(i));
                higestDegreePos = i;
            }
        }
        this.vertices.get(higestDegreePos).c = Color.BLUE;

        return this;
    }
}

class VertexTest {
    String name;
    int x, y;
    Color c;

    public VertexTest(String name, int x, int y, Color c) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.c = c;
    }
}

class EdgeTest {
    VertexTest v1, v2;
    public EdgeTest(VertexTest v1, VertexTest v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
}