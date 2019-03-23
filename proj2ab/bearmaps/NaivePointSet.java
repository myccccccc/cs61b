package bearmaps;
import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> l;

    public NaivePointSet(List<Point> points) {
        l = points;
    }
    @Override
    public Point nearest(double x, double y) {
        //Returns the closest point to the inputted coordinates.
        Point goalPoint = new Point(x, y);
        Point bestPoint = null;
        double bestDistance = Double.POSITIVE_INFINITY;
        for (Point p : l) {
            if (Point.distance(p, goalPoint) < bestDistance) {
                bestPoint = p;
                bestDistance = Point.distance(p, goalPoint);
            }
        }
        return bestPoint;
    }

}
