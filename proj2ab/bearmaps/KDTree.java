package bearmaps;

import java.util.List;
import java.util.Comparator;

public class KDTree implements PointSet {

    private Node head;

    public KDTree(List<Point> points) {
        head = null;
        for (Point p : points) {
            head = add(head, p, true);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        return nearestHelper(head, new Point(x, y), head).getPoint();
    }

    private Node nearestHelper(Node n, Point p, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.getPoint(), p) < Point.distance(best.getPoint(), p)) {
            best = n;
        }
        Node goodSide;
        Node badSide;
        if (n.myCompare(p) >= 0) {
            goodSide = n.rightChild;
            badSide = n.leftChild;
        } else {
            goodSide = n.leftChild;
            badSide = n.rightChild;
        }
        best = nearestHelper(goodSide, p, best);
        if (n.worthIt(p, best.getPoint()) > 0) {
            best = nearestHelper(badSide, p, best);
        }
        return best;
    }

    private Node add(Node n, Point p, boolean partition) {
        if (n == null) {
            return new Node(partition, p);
        }
        if (n.point.equals(p)) {
            return n;
        }
        if (n.myCompare(p) >= 0) {
            n.rightChild = add(n.rightChild, p, !partition);
        } else {
            n.leftChild = add(n.leftChild, p, !partition);
        }
        return n;
    }


    private static class XComparator implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(p2.getX(), p1.getX());
        }

    }

    private static class YComparator implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(p2.getY(), p1.getY());
        }

    }

    private static Comparator<Point> getComparator(boolean p) {
        if (p) {
            return new XComparator();
        } else {
            return new YComparator();
        }
    }

    private class Node {
        private boolean partition; //if true partition subspace into left and right (by x)
        private Point point;
        private Comparator<Point> c;
        private Node leftChild;
        private Node rightChild;
        double x;
        double y;
        Node(boolean partition, Point point) {
            this.partition = partition;
            this.point = point;
            this.c = getComparator(partition);
            this.leftChild = null;
            this.rightChild = null;
            this.x = point.getX();
            this.y = point.getY();
        }
        public int myCompare(Point p) {
            return c.compare(this.point, p);
        }
        public Point getPoint() {
            return this.point;
        }
        public int worthIt(Point goal, Point best) {
            if (partition) {
                double a = Math.pow(this.point.getX() - goal.getX(), 2);
                return Double.compare(Point.distance(best, goal), a);
            } else {
                double b = Math.pow(this.point.getY() - goal.getY(), 2);
                return Double.compare(Point.distance(best, goal), b);
            }
        }
    }

}
