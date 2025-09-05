package org.Bjornbak;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawPanel extends JPanel implements MouseListener, ItemListener, ChangeListener {
    public Graph graph = new Graph();
    private Boolean mouseDown;
    private Boolean drawIntermediatePoints = true;
    private Vertex startVertex;
    private ArrayList<JLabel> vertexLabels = new ArrayList<>();
    private double mosPosX, mosPosY;

    DrawPanel() {
        this.setPreferredSize(new Dimension(1000, 800));
        this.setLayout(null);
        addMouseListener(this);
        /*
        String[] input = {
                "1  4  2 14 21 13",
                "2  3  3 14  1",
                "3  4  4 15 14  2",
                "4  3  5 15  3",
                "5  4  6 16 15  4",
                "6  4  7 17 16  5",
                "7  5  8 22 18 17  6",
                "8  3  9 22  7",
                "9  4  10 19 22  8",
                "10  3  11 19  9",
                "11  4  12 20 19 10",
                "12  4  13 21 20 11",
                "13  3  1 21 12",
                "14 10  2 3 15 16 17 18 19 20 21 1",
                "15  5  3 4 5 16 14",
                "16  5  15 5 6 17 14",
                "17  5  16 6 7 18 14",
                "18  5  17 7 22 19 14",
                "19  7  18 22 9 10 11 20 14",
                "20  5  19 11 12 21 14",
                "21  5  20 12 13 1 14",
                "22  5  18 7 8 9 19"
        };

        String[] header = input[1].trim().split("\\s+");
        int V = input.length;
        graph = new Graph();
        Map<Integer, Vertex> vertexMap = new HashMap<>();
        int radius = 200; // circle radius
        int centerX = 300, centerY = 300;
        for (int i = 1; i <= V; i++) {
            double angle = 2 * Math.PI * (i - 1) / V;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            Vertex v = new Vertex("v" + i, x, y, null);
            graph.addVertex(v);
            vertexMap.put(i, v);
        }

        for (int i = 2; i < input.length; i++) {
            String[] parts = input[i].trim().split("\\s+");
            int vertex = Integer.parseInt(parts[0]);
            int degree = Integer.parseInt(parts[1]);

            for (int j = 0; j < degree; j++) {
                int neighbor = Integer.parseInt(parts[2 + j]);
                if (vertex < neighbor) {
                    graph.addEdge(vertexMap.get(vertex), vertexMap.get(neighbor));
                }
            }
        } */
        Vertex v1 = new Vertex("v1", 100 ,100, null);
        Vertex v2 = new Vertex("v2", 200 ,100, null);
        Vertex v3 = new Vertex("v3", 100 ,200, null);
        Vertex v4 = new Vertex("v4", 200 ,200, null);
        Vertex v5 = new Vertex("v5", 300 ,300, null);
        Vertex v6 = new Vertex("v6", 400 ,400, null);

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);

        graph.addEdge(v1, v2);
        graph.addEdge(v2, v3);
        graph.addEdge(v3, v4);
        graph.addEdge(v4, v1);






        for(int i = 0; i < graph.vertices.size(); i++) {
            JLabel label = new JLabel(graph.vertices.get(i).name);
            label.setBounds(graph.vertices.get(i).x + 15, graph.vertices.get(i).y - 15, 15, 15);
            vertexLabels.add(label);
            add(label);
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

        JToggleButton drawIntermediatePointsButton = new JToggleButton("Tegn kant punkter");
        drawIntermediatePointsButton.setBounds(100, 0, 150, 20);
        drawIntermediatePointsButton.setVisible(true);
        drawIntermediatePointsButton.setSelected(true);
        this.add(drawIntermediatePointsButton);
        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if (state == ItemEvent.SELECTED) drawIntermediatePoints = true;
                else drawIntermediatePoints = false;
                repaint();
            }
        };

        drawIntermediatePointsButton.addItemListener(itemListener);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        //Draw edges

            for (int i = 0; i < graph.edges.size(); i++) {
                g2d.setColor(Color.black);
                Edge currentEdge = graph.edges.get(i);

                Point2D p1 = new Point2D.Float(currentEdge.v1.x, currentEdge.v1.y);
                Point2D p2 = new Point2D.Float(currentEdge.v2.x, currentEdge.v2.y);
                Point2D intermediatePoint1 = new Point2D.Double(currentEdge.intermediatePoints[0].getX(), currentEdge.intermediatePoints[0].getY());
                Point2D intermediatePoint2 = new Point2D.Double(currentEdge.intermediatePoints[1].getX(), currentEdge.intermediatePoints[1].getY());
                Point2D[] edgePoint = {p1, intermediatePoint1, intermediatePoint2, p2};
                Point2D prevPoint = p1;
                BezierCurve curve = new BezierCurve(edgePoint);
                for (double t = 0; t <= 1; t += 0.01) {
                    Point2D currentPoint = curve.deCasteljau(edgePoint, t);
                    g2d.drawLine((int) currentPoint.getX(), (int) currentPoint.getY(), (int) prevPoint.getX(), (int) prevPoint.getY());
                    prevPoint = currentPoint;
                }
                if (drawIntermediatePoints) {
                    g2d.fillOval((int) intermediatePoint1.getX() - 3, (int) intermediatePoint1.getY() - 3, 6, 6);
                    g2d.fillOval((int) intermediatePoint2.getX() - 3, (int) intermediatePoint2.getY() - 3, 6, 6);
                }
            }

            //Draw verticies
            for (int i = 0; i < graph.vertices.size(); i++) {
                if (graph.vertices.get(i).c != null) {
                    g2d.setColor(graph.vertices.get(i).c);
                } else g2d.setColor(Color.black);

                if (i < vertexLabels.size()) {
                    vertexLabels.get(i).setBounds(graph.vertices.get(i).x + 15, graph.vertices.get(i).y - 15, 15, 15);
                } else {
                    // Create new label if needed
                    JLabel label = new JLabel(graph.vertices.get(i).name);
                    label.setBounds(graph.vertices.get(i).x + 15, graph.vertices.get(i).y - 15, 25, 15);
                    vertexLabels.add(label);
                    add(label);
                }
                Point2D p = new Point2D.Float(graph.vertices.get(i).x, graph.vertices.get(i).y);
                g2d.fillOval((int) p.getX() - 13, (int) p.getY() - 13, 26, 26);
            }

            if (startVertex != null) {
                g2d.setColor(Color.black);
                g2d.drawLine(startVertex.x, startVertex.y, (int) mosPosX, (int) mosPosY);
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
        // trykker på scroll wheel
        else if(e.getButton() == MouseEvent.BUTTON2){
            for(int i = 0; i < graph.vertices.size(); i++) {
                double distance = Math.sqrt(Math.pow(graph.vertices.get(i).x-e.getX(),2) + Math.pow(graph.vertices.get(i).y-e.getY(),2));
                if(distance <= 15){
                    graph.removeVertex(graph.vertices.get(i));
                }
            }
            repaint();
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
            for(int i = 0; i < graph.edges.size(); i++) {
                double distanceToIntermediatePoint1 = Math.sqrt(Math.pow(graph.edges.get(i).intermediatePoints[0].getX()-e.getX(),2) + Math.pow(graph.edges.get(i).intermediatePoints[0].getY()-e.getY(),2));
                double distanceToIntermediatePoint2 = Math.sqrt(Math.pow(graph.edges.get(i).intermediatePoints[1].getX()-e.getX(),2) + Math.pow(graph.edges.get(i).intermediatePoints[1].getY()-e.getY(),2));
                if(distanceToIntermediatePoint1 <= 15) {
                    MovingIntermediatePoint(graph.edges.get(i).intermediatePoints[0]);
                }
                else if(distanceToIntermediatePoint2 <= 15) {
                    MovingIntermediatePoint(graph.edges.get(i).intermediatePoints[1]);
                }
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
                            graph.ResetEdges(graph.getEdges(v));
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
                              graph.ResetColors();
                        }catch (Exception ignored) {

                        }

                        repaint();
                    } while (mouseDown);
                    isRunning = false;
                }
            }.start();
        }
    }

    private void MovingIntermediatePoint(Point2D p) {
        if (checkAndMark()) {
            new Thread() {
                public void run() {
                    do {
                        try {
                            p.setLocation(getMousePosition().x, getMousePosition().y);

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
