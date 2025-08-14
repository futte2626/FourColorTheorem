package org.Bjornbak;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class DrawPanel extends JPanel implements MouseListener, ItemListener, ChangeListener {
    public Graph graph;

    DrawPanel() {
        this.setPreferredSize(new Dimension(1000, 800));
        graph = new Graph();
        addMouseListener(this);

        Vertex v1 = new Vertex("v1", 100,200,null);
        Vertex v2 = new Vertex("v2",200,500,null);
        Vertex v3 = new Vertex("v3",300,200,null);
        Vertex v4 = new Vertex("v4",300,600,null);
        Vertex v5 = new Vertex("v5",500,100,null);


        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v2, v3);
        graph.addEdge(v3, v4);
        graph.addEdge(v4, v5);
        graph.addEdge(v5, v1);
        graph.removeVertex(v4);
        graph.printVertices(graph.getNeighbors(v1));
        graph.ColorGraph(5);
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

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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
}
