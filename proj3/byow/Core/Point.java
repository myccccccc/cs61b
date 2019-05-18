package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Point {
    protected int x;
    protected int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean canWeconnect(Point p, TETile[][] t) {
        int startX, startY, endX, endY;

        if (x < p.x) {
            startX = x;
            endX = p.x;
        } else {
            startX = p.x;
            endX = x;
        }
        if (y < p.y) {
            startY = y;
            endY = p.y;
        } else {
            startY = p.y;
            endY = y;
        }

        int counter = 0;
        if (x == p.x) {
            for (int j = startY; j < endY; j++) {
                if (t[x - 1][j] == Tileset.FLOWER || t[x + 1][j] == Tileset.FLOWER) {
                    counter++;
                }
            }
        } else {
            for (int i = startX; i < endX; i++) {
                if (t[i][y - 1] == Tileset.FLOWER || t[i][y + 1] == Tileset.FLOWER) {
                    counter++;
                }
            }
        }
        return counter <= 7;
    }

    public void connect(Point p, TETile[][] t) {
        int startX, startY, endX, endY;

        if (x < p.x) {
            startX = x;
            endX = p.x;
        } else {
            startX = p.x;
            endX = x;
        }
        if (y < p.y) {
            startY = y;
            endY = p.y;
        } else {
            startY = p.y;
            endY = y;
        }
        if (x == p.x) {
            for (int j = startY; j <= endY; j++) {
                t[x][j] = Tileset.FLOWER;
            }

        } else {
            for (int i = startX; i <= endX; i++) {
                t[i][y] = Tileset.FLOWER;
            }
        }
    }


}
