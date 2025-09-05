package org.Bjornbak;

import java.awt.geom.Point2D;

public class Edge {
    public Vertex v1, v2;
    public Point2D[] intermediatePoints;

    public Edge(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
        this.intermediatePoints = new Point2D[2];
        intermediatePoints[0] = new Point2D.Double((1-0.33)*v1.x + 0.33*v2.x, (1-0.33)*v1.y + 0.33*v2.y);
        intermediatePoints[1] = new Point2D.Double((1-0.66)*v1.x + 0.66*v2.x, (1-0.66)*v1.y + 0.66*v2.y);
    }

    public void CalculateIntermediatePoints() {
        intermediatePoints[0] = new Point2D.Double((1-0.33)*v1.x + 0.33*v2.x, (1-0.33)*v1.y + 0.33*v2.y);
        intermediatePoints[1] = new Point2D.Double((1-0.66)*v1.x + 0.66*v2.x, (1-0.66)*v1.y + 0.66*v2.y);
    }
}
