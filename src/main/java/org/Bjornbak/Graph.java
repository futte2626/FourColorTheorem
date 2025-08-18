package org.Bjornbak;

import java.awt.*;
import java.util.ArrayList;

public final class Graph {
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;
    ArrayList<Color> colors;

    public Graph() {
        vertices = new ArrayList<>(0);
        edges = new ArrayList<>(0);
        colors = new ArrayList<>(4);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public void addVertex(String name, int x, int y, Color color) {
        vertices.add(new Vertex(name, x, y, color));
    }

    public void removeVertex(Vertex v) {
        ArrayList<Edge> toRemove = new ArrayList<>();
        for (Edge e : edges) {
            if (e.v1.equals(v) || e.v2.equals(v)) {
                toRemove.add(e);
            }
        }
        edges.removeAll(toRemove);
        vertices.remove(v);
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
                if(e.v1.equals(v) || e.v2.equals(v)) {
                    System.out.println("Edge: " + e.v1.name + " --> " + e.v2.name);
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
            if(e.v1.equals(v)) {
                degree++;
            }
        }
        return degree;
    }

    public ArrayList<Vertex> getNeighbors(Vertex v) {
        ArrayList<Vertex> neighbors = new ArrayList<>();
        for (Edge e : edges) {
            if(e.v1.equals(v)) {
                neighbors.add(e.v2);
            }
            else if(e.v2.equals(v)) {
                neighbors.add(e.v1);
            }
        }

        return neighbors;
    }

    public ArrayList<String> getNeighborName(Vertex v) {
        ArrayList<String> neighbors = new ArrayList<>();
        for (Edge e : edges) {
            if(e.v1.equals(v)) {
                neighbors.add(e.v2.name);
            }
        }

        return neighbors;
    }

    public void ColorGraph() {
        double startTime = System.nanoTime();
        for (Vertex v : vertices) {
            // fresh copy of all colors for this vertex
            ArrayList<Color> availableColors = new ArrayList<>(colors);

            // remove colors already used by neighbors
            for (Vertex neighbor : getNeighbors(v)) {
                if (neighbor.c != null) {
                    availableColors.remove(neighbor.c);
                }
            }

            // assign the first available color
            if (!availableColors.isEmpty()) {
                v.c = availableColors.get(0);
            } else {
                v.c = Color.BLACK; // fallback if no color is available
            }
        }
        System.out.println("Time to color graph: " + ((System.nanoTime() - startTime) / 1000000) + " ms");
    }

    /*public void ColorGraph() {
        ArrayList<Color> possibleColors = colors;
        for (int i = 0; i < this.vertices.size(); i++) {
            for(Vertex v : getNeighbors(vertices.get(i))) {
                if(v.c != null) possibleColors.remove(v.c);
            }
            if(!possibleColors.isEmpty()) {vertices.get(i).c = possibleColors.get(0);}
            else vertices.get(i).c = Color.BLACK;

            possibleColors = colors;
        }
    } */

    /* public void ColorGraph(int maxColor) {
        int highestDegree = 0;
        int higestDegreePos = 0;
        for (int i = 0; i < this.vertices.size(); i++) {
            if(getVertexDegree(this.vertices.get(i)) > highestDegree) {
                highestDegree = getVertexDegree(this.vertices.get(i));
                higestDegreePos = i;
            }
        }
        this.vertices.get(higestDegreePos).c = Color.BLUE;
    } */
}