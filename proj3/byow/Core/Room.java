package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {

    public static final int MAXWIDTH = 3;
    public static final int MAXHEIGHT = 3;
    public static final int MINWIDTH = 2;
    public static final int MINHEIGHT = 2;

    private Point uperLeft;
    private int xlength;
    private int ylength;

    public Room(TETile[][] world, Random r) {
        while (true) {
            int randomX = r.nextInt(Engine.WIDTH - 2) + 1;
            int randomY = r.nextInt(Engine.HEIGHT - 3) + 1;
            Point newP = new Point(randomX, randomY);
            int randomXlength = r.nextInt(Room.MAXWIDTH - Room.MINWIDTH + 1) + Room.MINWIDTH;
            int randomYlength = r.nextInt(Room.MAXHEIGHT - Room.MINHEIGHT + 1) + Room.MINHEIGHT;
            boolean flag = true;

            if (randomX + randomXlength >= Engine.WIDTH - 2) {
                continue;
            }
            if (randomY - randomYlength <= 1) {
                continue;
            }

            for (int i = newP.x; i <= newP.x + randomXlength; i++) {
                for (int j = newP.y - randomYlength; j <= newP.y; j++) {
                    if (!(world[i][j] == Tileset.NOTHING)) {
                        flag = false;
                        break;
                    }
                    if (!(world[i + 1][j] == Tileset.NOTHING)) {
                        flag = false;
                        break;
                    }
                    if (!(world[i - 1][j] == Tileset.NOTHING)) {
                        flag = false;
                        break;
                    }
                    if (!(world[i][j + 1] == Tileset.NOTHING)) {
                        flag = false;
                        break;
                    }
                    if (!(world[i][j - 1] == Tileset.NOTHING)) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    break;
                }
            }
            if (!flag) {
                continue;
            }
            for (int i = newP.x; i <= newP.x + randomXlength; i++) {
                for (int j = newP.y - randomYlength; j <= newP.y; j++) {
                    world[i][j] = Tileset.FLOOR;
                }
            }
            uperLeft = newP;
            xlength = randomXlength;
            ylength = randomYlength;
            break;
        }
    }

    public boolean connectRoom(Random r, Room anotherRoom, TETile[][] world) {
        List<Edge> l1 = this.getEdges();
        List<Edge> l2 = anotherRoom.getEdges();
        return l1.get(r.nextInt(4)).connectEdge(l2.get(r.nextInt(4)), world, r);
    }
    public int getDis(Room room) {
        return (this.uperLeft.x - room.uperLeft.x) * (this.uperLeft.x - room.uperLeft.x)
                + (this.uperLeft.y - room.uperLeft.y) * (this.uperLeft.y - room.uperLeft.y);
    }
    private List<Edge> getEdges() {
        List<Edge> l = new ArrayList<>();
        Point p1, p2;
        p1 = new Point(uperLeft.x, uperLeft.y);
        p2 = new Point(uperLeft.x + xlength, uperLeft.y);
        l.add(new Edge(p1, p2));

        p1 = new Point(uperLeft.x, uperLeft.y - ylength);
        p2 = new Point(uperLeft.x, uperLeft.y);
        l.add(new Edge(p1, p2));

        p1 = new Point(uperLeft.x, uperLeft.y  - ylength);
        p2 = new Point(uperLeft.x + xlength, uperLeft.y - ylength);
        l.add(new Edge(p1, p2));

        p1 = new Point(uperLeft.x + xlength, uperLeft.y  - ylength);
        p2 = new Point(uperLeft.x + xlength, uperLeft.y);
        l.add(new Edge(p1, p2));
        return l;
    }

    public void redraw(TETile[][] world) {
        for (int i = uperLeft.x; i <= uperLeft.x + xlength; i++) {
            for (int j = uperLeft.y - ylength; j <= uperLeft.y; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }

}
