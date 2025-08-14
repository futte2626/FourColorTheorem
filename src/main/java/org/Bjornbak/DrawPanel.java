package org.Bjornbak;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class DrawPanel extends JPanel implements MouseListener, ItemListener, ChangeListener {
    public Graph graph;
    private Boolean mouseDown;
    private Vertex startVertex;
    private double mosPosX, mosPosY;

    DrawPanel() {
        this.setPreferredSize(new Dimension(1000, 800));
        graph = new Graph();
        addMouseListener(this);


        Vertex v1 = new Vertex("v1", 100, 100, null);
        Vertex v2 = new Vertex("v2", 200, 50, null);
        Vertex v3 = new Vertex("v3", 300, 100, null);
        Vertex v4 = new Vertex("v4", 150, 200, null);
        Vertex v5 = new Vertex("v5", 250, 200, null);
        Vertex v6 = new Vertex("v6", 100, 300, null);
        Vertex v7 = new Vertex("v7", 300, 300, null);

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);

// Planar edges
      /*  graph.addEdge(v1, v2);
        graph.addEdge(v2, v3);
        graph.addEdge(v1, v4);
        graph.addEdge(v2, v4);
        graph.addEdge(v2, v5); */
        graph.addEdge(v3, v5);
        graph.addEdge(v4, v5);
        graph.addEdge(v4, v6);
        graph.addEdge(v5, v7);
        graph.addEdge(v6, v7);
        graph.printVertices(graph.getNeighbors(v1));
        JButton colorButton = new JButton("Color graph");
        colorButton.setSize(400,400);
        colorButton.setVisible(true);
        this.add(colorButton);
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graph.ColorGraph();
                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        //Draw edges
        for(int i = 0; i < graph.edges.size(); i++) {
            g2d.setColor(Color.black);
            Point2D p1 = new Point2D.Float(graph.edges.get(i).a.x, graph.edges.get(i).a.y);
            Point2D p2 = new Point2D.Float(graph.edges.get(i).b.x, graph.edges.get(i).b.y);
            g2d.drawLine((int)p1.getX(),(int)p1.getY(),(int)p2.getX(),(int)p2.getY());
        }

        //Draw verticies
        for(int i = 0; i < graph.vertices.size(); i++) {
            if(graph.vertices.get(i).c != null) {
                g2d.setColor(graph.vertices.get(i).c);
            }
            else g2d.setColor(Color.black);

            Point2D p = new Point2D.Float(graph.vertices.get(i).x, graph.vertices.get(i).y);
            g2d.fillOval((int)p.getX()-13, (int)p.getY()-13, 26, 26);
        }

        if(startVertex != null) {
            g2d.setColor(Color.black);
            g2d.drawLine(startVertex.x,startVertex.y,(int)mosPosX,(int)mosPosY);
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getButton());
        //højre klik
        if(e.getButton() == MouseEvent.BUTTON3){
            graph.addVertex("v" + (graph.vertices.size()+1),e.getX(), e.getY(),null);
            repaint();
        }
        // scroll wheel
        else if(e.getButton() == MouseEvent.BUTTON2){
            for(int i = 0; i < graph.vertices.size(); i++) {
                double distance = Math.sqrt(Math.pow(graph.vertices.get(i).x-e.getX(),2) + Math.pow(graph.vertices.get(i).y-e.getY(),2));
                if(distance <= 15) graph.removeVertex(graph.vertices.get(i));
            }
            repaint();
            graph.printVertices(graph.vertices);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseDown = true;
            for(int i = 0; i < graph.vertices.size(); i++) {
                double distance = Math.sqrt(Math.pow(graph.vertices.get(i).x-e.getX(),2) + Math.pow(graph.vertices.get(i).y-e.getY(),2));
                if(distance <= 15) MovingVertex(graph.vertices.get(i));
            }

        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            mouseDown = true;
            for(int i = 0; i < graph.vertices.size(); i++) {
                double distance = Math.sqrt(Math.pow(graph.vertices.get(i).x-e.getX(),2) + Math.pow(graph.vertices.get(i).y-e.getY(),2));
                if(distance <= 15) {
                    startVertex = graph.vertices.get(i);
                    AddingEdge(startVertex);
                }
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseDown = false;
            repaint();
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            mouseDown = false;
            for(int i = 0; i < graph.vertices.size(); i++) {
                double distance = Math.sqrt(Math.pow(graph.vertices.get(i).x-e.getX(),2) + Math.pow(graph.vertices.get(i).y-e.getY(),2));
                if(distance <= 15 && !graph.vertices.get(i).equals(startVertex)) {
                    graph.addEdge(graph.vertices.get(i), startVertex);
                }
            }
            startVertex = null;
            repaint();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }

    volatile private boolean isRunning = false;
    private synchronized boolean checkAndMark() {
        if (isRunning) return false;
        isRunning = true;
        return true;
    }
    private void MovingVertex(Vertex v) {
        if (checkAndMark()) {
            new Thread() {
                public void run() {
                    do {
                        try {
                            v.x = getMousePosition().x;
                            v.y = getMousePosition().y;
                        }catch (Exception ignored) {

                        }

                        repaint();
                    } while (mouseDown);
                    isRunning = false;
                }
            }.start();
        }
    }

    private void AddingEdge(Vertex v) {
        if (checkAndMark()) {
            new Thread() {
                public void run() {
                    do {
                        try {
                              mosPosX = getMousePosition().x;
                              mosPosY = getMousePosition().y;
                        }catch (Exception ignored) {

                        }

                        repaint();
                    } while (mouseDown);
                    isRunning = false;
                }
            }.start();
        }
    }
}
