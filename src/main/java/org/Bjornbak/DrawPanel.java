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
    private JLabel[] vertexLabels;
    private double mosPosX, mosPosY;

    DrawPanel() {
        this.setPreferredSize(new Dimension(1000, 800));
        this.setLayout(null);
        graph = new Graph();
        addMouseListener(this);


        Vertex r = new Vertex("r", 60, 60, null);
        Vertex g = new Vertex("g", 140, 60, null);
        Vertex b = new Vertex("b", 100, 120, null);

// the four neighbors of u (will end up R,G,B,Y)
        Vertex n1 = new Vertex("n1", 260, 40, null);
        Vertex n2 = new Vertex("n2", 320, 100, null);
        Vertex n3 = new Vertex("n3", 260, 160, null);
        Vertex n4 = new Vertex("n4", 200, 100, null);

// center (must be last to force failure)
        Vertex u  = new Vertex("u", 280, 100, null);

// add in THIS order to preserve iteration order of ColorGraph()
        graph.addVertex(r);
        graph.addVertex(g);
        graph.addVertex(b);
        graph.addVertex(n1);
        graph.addVertex(n2);
        graph.addVertex(n3);
        graph.addVertex(n4);
        graph.addVertex(u);

// --- helper triangle to fix their colors: r=RED, g=GREEN, b=BLUE
        graph.addEdge(g, r);      // g sees RED -> becomes GREEN
        graph.addEdge(b, r);      // b sees RED & GREEN (after next line) -> becomes BLUE
        graph.addEdge(b, g);

// --- force n1..n4 to become R,G,B,Y in order
// n1 has no colored neighbors -> RED
// n2 adjacent to n1(RED) -> GREEN
        graph.addEdge(n1, n2);

// n3 adjacent to n2(GREEN) and r(RED) -> BLUE
        graph.addEdge(n3, n2);
        graph.addEdge(n3, r);

// n4 adjacent to r(RED), g(GREEN), b(BLUE) -> YELLOW
        graph.addEdge(n4, r);
        graph.addEdge(n4, g);
        graph.addEdge(n4, b);

// --- center sees all four colors -> no color left -> BLACK
        graph.addEdge(u, n1);
        graph.addEdge(u, n2);
        graph.addEdge(u, n3);
        graph.addEdge(u, n4);

        vertexLabels = new JLabel[graph.vertices.size()];
        for(int i = 0; i < vertexLabels.length; i++) {
            vertexLabels[i] = new JLabel(graph.vertices.get(i).name);
            vertexLabels[i].setBounds(graph.vertices.get(i).x + 15,graph.vertices.get(i).y - 15, 15, 15 );
            add(vertexLabels[i]);
        }

        JButton colorButton = new JButton("Farv graf");
        colorButton.setBounds(0, 0, 100, 20);
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

            vertexLabels[i].setBounds(graph.vertices.get(i).x + 15,graph.vertices.get(i).y - 15, 15, 15 );
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
