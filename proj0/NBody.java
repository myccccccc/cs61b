public class NBody {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radius, 2 * radius);
        for (int i = 0; i < bodies.length; i++) {
            bodies[i].draw();
        }
        double time = 0;
        double[] xForces = new double[bodies.length];
        double[] yForces = new double[bodies.length];
        for (; time <= T; time += dt) {
            for (int i = 0; i < bodies.length; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radius, 2 * radius);
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", bodies[i].xxPos, bodies[i].yyPos,
                    bodies[i].xxVel, bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }

    public static double readRadius(String filename) {
        In in = new In(filename);
        int num_body = in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String filename) {
        In in = new In(filename);
        int num_body = in.readInt();
        Body[] Bodies = new Body[num_body];
        in.readDouble();
        for (int i = 0; i < num_body; i++) {
            Bodies[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(),
                    in.readString());
        }
        return Bodies;
    }
}