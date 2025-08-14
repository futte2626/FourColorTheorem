package org.Bjornbak;

import java.awt.*;
import java.util.ArrayList;

public class Vertex {
    String name;
    int x, y;
    Color c;
    ArrayList<Edge> edges;

    public Vertex(String name, int x, int y, Color c) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.c = c;
        edges = new ArrayList<>(0);
    }

    public void AddEdge(Vertex v2) {
        if (!edges.contains(v2)) {
            edges.add(new Edge(this, v2));
            v2.edges.add(new Edge(v2, this));
        }
    }

    public void PrintVertex() {
        System.out.println("Vertex: " + name);
        for (Edge e : edges) {
            System.out.println("Edge: " + e.a.name + " <-> " + e.b.name);
        }
    }
}
