package bearmaps;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;

public class KDTreeTest {

    private static Random r = new Random(500);

    @Test
    public void basictest() {
        Point A = new Point(2, 3);
        Point Z = new Point(4, 2);
        Point B = new Point(4, 2);
        Point C = new Point(4, 5);
        Point D = new Point(3, 3);
        Point E = new Point(1, 5);
        Point F = new Point(4, 4);
        KDTree t = new KDTree(List.of(A, Z, B, C, D, E, F));
        Point ret = t.nearest(0, 7);
        assertEquals(ret, new Point(1, 5));

        KDTree g = new KDTree(List.of(A, Z, B));
        assertEquals(g.nearest(2, 3), new Point(2, 3));

        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret2 = nn.nearest(3.0, 4.0); // returns p2
        assertEquals(ret2, new Point(3.3, 4.4));
    }

    @Test
    public void randomtest() {
        List<Point> points = new ArrayList<>();
        List<Point> queryPoints = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            points.add(new Point(r.nextDouble(), r.nextDouble()));
        }
        for (int i = 0; i < 1000; i++) {
            queryPoints.add(new Point(r.nextDouble(), r.nextDouble()));
        }
        KDTree t = new KDTree(points);
        NaivePointSet n = new NaivePointSet(points);
        for (Point q : queryPoints) {
            Point p1 = t.nearest(q.getX(), q.getY());
            Point p2 = n.nearest(q.getX(), q.getY());
            System.out.println(p1.getX() + " "  + p1.getY());
            System.out.println(p2.getX() + " "  + p2.getY());
            assertTrue(points.contains(p1));
            assertTrue(points.contains(p2));
            assertEquals(p1, p2);
        }
    }

    @Test
    public void timetest() {
        List<Point> points = new ArrayList<>();
        List<Point> queryPoints = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            points.add(new Point(r.nextDouble(), r.nextDouble()));
        }
        for (int i = 0; i < 10000; i++) {
            queryPoints.add(new Point(r.nextDouble(), r.nextDouble()));
        }
        KDTree t = new KDTree(points);
        NaivePointSet n = new NaivePointSet(points);

        Stopwatch sw1 = new Stopwatch();
        for (Point q : queryPoints) {
            Point p1 = t.nearest(q.getX(), q.getY());
        }
        System.out.println(sw1.elapsedTime());

        Stopwatch sw2 = new Stopwatch();
        for (Point q : queryPoints) {
            Point p1 = n.nearest(q.getX(), q.getY());
        }
        System.out.println(sw2.elapsedTime());
    }
}
