import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;

    //////////////////////////////////////////////////////////////////
    private class Node {
        private Node left, right;
        private int size;
        private Point2D point;
        double key;
        boolean horizontal;

        Node(Point2D point, Double key, int size, boolean horizontal) {
            this.point = point;
            this.size = size;
            this.key = key;
            this.horizontal = horizontal;

        }
    }

    ///////////////////////////////////////////////////////////////////////////
    private int compareTo(Node x, Point2D that) {
        if (!x.horizontal) {
            return Double.compare(x.point.x(), that.x());
        }
        else {
            return Double.compare(x.point.y(), that.y());
        }
    }


    ////////////////////////////////////////////////////////////////////////
    public KdTree() {
    }

    ////////////////////////////////////////////////////////////////////////////
    public boolean isEmpty() {
        return size() == 0;
    }

    ////////////////////////////////////////////////////////////////////////
    public int size() {
        return (size(root));
    }

    private int size(Node n) {
        if (n == null) return 0;
        return n.size;
    }

    ///////////////////////////////////////////////////////////////////////////
    private boolean curr_horizontal = true;

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) {
            double key = curr_horizontal ? p.x() : p.y();
            root = insert(root, p, key);
            curr_horizontal = !curr_horizontal;
        }
        else if (!contains(p)) {
            double key = curr_horizontal ? p.x() : p.y();
            root = insert(root, p, key);
            curr_horizontal = !curr_horizontal;
        }
    }

    private Node insert(Node x, Point2D point, Double key) {
        if (x == null) {
            //System.out.println(curr_horizontal ? "horizontal" : "vertical");
            // System.out.println(key);
            return new Node(point, key, 1, curr_horizontal);
        }
        double cmp = key - (x.key);
        if (cmp < 0) {
            if (x.left == null) curr_horizontal = !x.horizontal;
            x.left = insert(x.left, point, key);
        }
        else if (cmp >= 0) {
            if (x.right == null) curr_horizontal = !x.horizontal;
            x.right = insert(x.right, point, key);
        }
        // else x.point = point;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /////////////////////////////////////////////////////////////////////////////////
    public boolean contains(Point2D p) {
        return (get(p.x(), p) != null || get(p.y(), p) != null);
    }

    private Point2D get(Double key, Point2D p) {
        return get(root, key, p);
    }

    private Point2D get(Node x, Double key, Point2D p) {
        // if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        if (x.point.equals(p)) return p;
        double cmp = key - (x.key);
        if (cmp < 0) return get(x.left, key, p);
        else if (cmp >= 0) return get(x.right, key, p);
        else return null;
    }
    ////////////////////////////////////////////////////////////////////////////////

    public void draw() {
        draw(root, 0, 0, 1, 1);
    }

    private void draw(Node x, double x_min, double y_min, double x_max, double y_max) {
        if (x == null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            x.point.draw();
        }

        if (x.horizontal) {
            StdDraw.setPenColor(StdDraw.BLUE);
            RectHV rect = new RectHV(x_min, x.point.y(), x_max, x.point.y());
            rect.draw();
            draw(x.right, x_min, x.point.y(), x_max, y_max);
            draw(x.left, x_min, y_min, x_max, x.point.y());
        }

        if (!x.horizontal) {
            StdDraw.setPenColor(StdDraw.RED);
            RectHV rect = new RectHV(x.point.x(), y_min, x.point.x(), y_max);
            rect.draw();
            draw(x.right, x.point.x(), y_min, x_max, y_max);
            draw(x.left, x_min, y_min, x.point.x(), y_max);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    private Stack<Point2D> in_range = new Stack<>();

    public Iterable<Point2D> range(RectHV rect) {
        range(rect, root);
        return in_range;
    }

    private void range(RectHV rect, Node curr) {
        if (curr == null) return;

        if (rect.contains(curr.point)) in_range.push(curr.point);


        double point_coord = curr.point.y();
        double rectMin = rect.ymin();
        double rectMax = rect.ymax();

        if (!curr.horizontal) {
            point_coord = curr.point.x();
            rectMin = rect.xmin();
            rectMax = rect.xmax();
        }
        if (point_coord > rectMin)
            range(rect, curr.left);
        if (point_coord <= rectMax)
            range(rect, curr.right);
    }

    private Point2D nearest_point;

    /////////////////////////////////////////////////////////////////////////////////////////
    public Point2D nearest(Point2D p) {
        nearest_point = root.point;
        //System.out.println(nearest_point + "NEARRSRSRS");
        nearest(p, root);
        return nearest_point;
    }

    // private Point2D final_ans;

    private void nearest(Point2D p, Node x) {
        //  System.out.println(x.point);
        // System.out.println(final_ans + " NEAREST");
        if (x == null) {
            //   System.out.println("NULL");
            return;
        }
        if (p.distanceSquaredTo(x.point) < p.distanceSquaredTo(nearest_point)) {
            nearest_point = x.point;
            //  System.out.println(final_ans + "MAIN");
            // min_dist = p.distanceSquaredTo(x.point);
        }
        //System.out.println(x.point);
        if (compareTo(x, p) > 0) {
            nearest(p, x.left);
            nearest(p, x.right);
        }
        else {
            nearest(p, x.right);
            nearest(p, x.left);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // draw left
    /////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        KdTree kd = new KdTree();
        Point2D p0 = new Point2D(0.7, 0.2);
        Point2D p1 = new Point2D(0.5, 0.4);
        Point2D p2 = new Point2D(0.2, 0.3);
        Point2D p3 = new Point2D(0.4, 0.7);
        Point2D near = new Point2D(0.9, 0.6);
        Point2D near2 = new Point2D(0.71, 0.968);
        // Point2D p0 = new Point2D(0.125, 0.5);
        // Point2D p1 = new Point2D(0.375, 0.625);
        // Point2D p2 = new Point2D(0.625, 0.875);
        // Point2D p3 = new Point2D(0.5, 0.375);
        // Point2D near = new Point2D(0.75, 0.75);
        // Point2D near2 = new Point2D(0.0, 0.0);
        kd.insert(p0);
        // System.out.println(kd.size());
        kd.insert(p1);
        // System.out.println(kd.size());
        kd.insert(p2);
        // System.out.println(kd.size());
        kd.insert(p3);
        // System.out.println(kd.size());
        kd.insert(near);
        // System.out.println(kd.size());
        //kd.insert(near2);
        // System.out.println(kd.contains(p0));
        // System.out.println(kd.contains(p2));
        // System.out.println(kd.size());
        // System.out.println(kd.isEmpty());
        System.out.println(kd.nearest(near2));
        //RectHV rect = new RectHV(0.125, 0.00, 1.0, 0.625);

        //RectHV rect = new RectHV(0.25, 0.125, 0.875, 1.0);
        //System.out.println(kd.range(rect));

    }
}
