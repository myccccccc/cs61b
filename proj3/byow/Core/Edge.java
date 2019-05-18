package byow.Core;

import byow.TileEngine.TETile;

import java.util.Random;

public class Edge {
    protected Point start, end;
    private boolean isHorizontal;
    public Edge(Point start, Point end) {
        this.start = start;
        this.end = end;
        if (this.start.x == this.end.x) {
            isHorizontal = false;
        } else {
            isHorizontal = true;
        }
    }
    private Point getRandomPoint(Random rand) {
        if (isHorizontal) {
            int newx = rand.nextInt(this.end.x - this.start.x) + this.start.x;
            return new Point(newx, this.start.y);
        } else {
            int newy = rand.nextInt(this.end.y - this.start.y) + this.start.y;
            return new Point(this.start.x, newy);
        }
    }
    public boolean connectEdge(Edge e2, TETile[][] world, Random rand) {
        Point p1 = this.getRandomPoint(rand);
        Point p2 = e2.getRandomPoint(rand);
        Point p3 = new Point(p1.x, p2.y);
        if (p1.canWeconnect(p3, world) && p2.canWeconnect(p3, world)) {
            p1.connect(p3, world);
            p2.connect(p3, world);
            return true;
        }
        return false;
    }
}
