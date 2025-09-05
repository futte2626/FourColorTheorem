package org.Bjornbak;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BezierCurve {
    public Point2D[] controlPoints = new Point2D[5];

    public BezierCurve(Point2D[] controlPoints) {
        this.controlPoints = controlPoints;
    }


    /*public Point2D getPoint(double t) {
        Point2D p = new Point2D.Double(0,0);
        int n = controlPoints.length;
        for(int i = 0; i < n; i++) {
            p.setLocation(p.getX()+ binomalCo(n, i)*Math.pow((1-t), n-i)*Math.pow(t,i)*controlPoints[i].getX(),p.getY()+ binomalCo(n, i)*Math.pow((1-t), n-i)*Math.pow(t,i)*controlPoints[i].getY());
        }
        return p;
    } */

    public Point2D deCasteljau(Point2D[] points, double t) {
        if (points.length == 1) {
            return points[0];
        }

        Point2D[] nextLevel = new Point2D[points.length - 1];
        for (int i = 0; i < nextLevel.length; i++) {
            double x = (1 - t) * points[i].getX() + t * points[i + 1].getX();
            double y = (1 - t) * points[i].getY() + t * points[i + 1].getY();
            nextLevel[i] = new Point2D.Double(x, y);

        }

        return deCasteljau(nextLevel, t);
    }

    long binomalCo(int n, int k) {
        return factorial(n)/(factorial(k)*factorial(n-k));
    }

    long factorial(int n) {
        if(n==0) return 1;
        int res = n;
        for (int i = n-1; i > 0; i--) {
            res *= i;
        }
        return res;
    }
}
