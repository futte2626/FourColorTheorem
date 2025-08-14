package org.Bjornbak;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static Graph g = new Graph();
    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1", 100,200,Color.red);
        Vertex v2 = new Vertex("v2",200,300,Color.green);
        Vertex v3 = new Vertex("v3",300,400,Color.yellow);

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(v1, v2);
        g.addEdge(v1, v3);
        g.addEdge(v2, v3);
        g.printGraph();
        g.printVertices(g.getNeighbors(v1));

        JFrame mainFrame = new JFrame("GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawPanel drawPanel = new DrawPanel();
        mainFrame.add(drawPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

}