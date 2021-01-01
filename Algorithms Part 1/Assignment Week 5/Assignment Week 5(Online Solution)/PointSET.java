import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (!points.contains(p)) points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<Point2D> inside = new ArrayList<>();

        for (Point2D p : points) {
            if (rect.contains(p)) inside.add(p);
        }
        return inside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        double max_dist = 0;
        Point2D nearest = null;
        for (Point2D p1 : points) {
            max_dist = p1.distanceTo(p);
            nearest = p1;
            break;
        }
        for (Point2D p1 : points) {
            if (p1.distanceTo(p) < max_dist) {
                max_dist = p1.distanceTo(p);
                nearest = p1;
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET pd = new PointSET();
        Point2D p = new Point2D(0, 0);
        Point2D p2 = new Point2D(0, 1);
        Point2D p3 = new Point2D(2, 0);
        Point2D p4 = new Point2D(-1, 0);
        pd.insert(p);
        pd.insert(p2);
        pd.insert(p3);

        System.out.println(pd.nearest(p4));

    }
}
