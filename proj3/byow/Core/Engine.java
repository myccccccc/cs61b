package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class Engine {
    private TERenderer ter;
    private TETile[][] world;
    private Random rand;
    private int health;
    private boolean hardMode;
    private Point doorLoc;
    private long startTime,endTime;
    private long lasttime;
    private List<Point> Portals;
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int HEARTSNUM = 3;
    public static final int PORTALNUM = 4;
    private long seed;

    public Engine() {
        this.world = null;
        this.rand = null;
        this.seed = 0;
        Portals = new ArrayList<>();
    }

    private TERenderer init(int width, int height) {
        TERenderer t = new TERenderer();
        health = 5;
        lasttime = 0;
        t.initialize(WIDTH, HEIGHT + 1, 0, 0);
        return t;
    }

    private Point getAvator() {
        Point pCur = new Point(0, 0);
        while (true) {
            int x = this.rand.nextInt(WIDTH);
            int y = this.rand.nextInt(HEIGHT);
            if (this.world[x][y] == Tileset.FLOOR) {
                this.world[x][y] = Tileset.AVATAR;
                pCur.x = x;
                pCur.y = y;
                break;
            }
        }
        return pCur;
    }

    public void interactWithKeyboard() {
        this.ter = init(Engine.WIDTH, Engine.HEIGHT);
        drawFrameHome();
        Point p = null;
        String nlq = getNLQ();
        if (nlq.equals("N") || nlq.equals("n")) {
            this.seed = drawGetSeed();
            this.rand = new Random(this.seed);
            this.world = newWorld();
            p = getAvator();
            for (int i = 0; i < Engine.WIDTH; i++) {
                for (int j = 0; j < Engine.HEIGHT; j++) {
                    if (this.world[i][j] == Tileset.PORTAL) {
                        this.Portals.add(new Point(i, j));
                    }
                }
            }
            drawChooseMode();
        } else if (nlq.equals("L") || nlq.equals("l")) {
            FileReader fr = null;
            try {
                fr = new FileReader("save.txt");
            } catch (IOException e) {
                System.exit(1);
            }
            int ch;
            String str = "";
            try {
                while ((ch = fr.read()) != -1) {
                    str += (char) ch;
                }
            } catch (IOException e) {
                System.exit(1);
            }
            //System.out.println(str);
            try {
                fr.close();
            } catch (IOException e) {
                System.exit(1);
            }
            String[] ans = str.split("#");
            long newSeed = Long.parseLong(ans[0]);
            this.seed = newSeed;
            this.rand = new Random(this.seed);
            int newX = Integer.parseInt(ans[1]);
            int newY = Integer.parseInt(ans[2]);
            health = Integer.parseInt(ans[3]);
            this.world = newWorld();
            p = new Point(newX, newY);
            world[newX][newY] = Tileset.AVATAR;
            for (int x = 0; x < Engine.WIDTH; x++) {
                for (int y = 0; y < Engine.HEIGHT; y++) {
                    if (this.world[x][y] == Tileset.HEART) {
                        this.world[x][y] = Tileset.FLOOR;
                    }
                }
            }
            int i = 4;
            while (i + 1 < ans.length - 3){
                this.world[Integer.parseInt(ans[i])][Integer.parseInt(ans[i + 1])] = Tileset.HEART;
                i += 2;
            }
            if (ans[ans.length - 3].equals(Character.toString('1'))) {
                hardMode = true;
            } else {
                hardMode = false;
            }
            lasttime = Long.parseLong(ans[ans.length - 1]);
            for (int ii = 0; ii < Engine.WIDTH; ii++) {
                for (int j = 0; j < Engine.HEIGHT; j++) {
                    if (this.world[ii][j] == Tileset.PORTAL) {
                        this.Portals.add(new Point(ii, j));
                    }
                }
            }
            //System.out.println(p.x);
        } else {
            System.exit(0);
        }
        drawHealth(health, p);
        TETile[][] hardWorld = becomeHard(world, p);
        if (!hardMode) {
            ter.renderFrame(world);
        } else {
            ter.renderFrame(hardWorld);
        }
        startTime = System.currentTimeMillis();
        gaming(p);

    }

    private char getnextChar() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
        char c = StdDraw.nextKeyTyped();
        return c;
    }

    private void gaming(Point p) {
        while (true) {
            drawHUD(p);
            drawHealth(health, p);
            drawTime();
            char c = getnextChar();
            if (c == ':') {
                c = getnextChar();
                if (c == 'q' || c == 'Q') {
                    String data = "";
                    data += seed;
                    data += '#';
                    data += p.x;
                    data += '#';
                    data += p.y;
                    data += '#';
                    data += health;
                    for (int x = 0; x < Engine.WIDTH; x++) {
                        for (int y = 0; y < Engine.HEIGHT; y++) {
                            if (this.world[x][y] == Tileset.HEART) {
                                data += '#';
                                data += x;
                                data += '#';
                                data += y;
                            }
                        }
                    }
                    if (hardMode) {
                        data += '#';
                        data += '1';
                    }
                    else {
                        data += '#';
                        data += '0';
                    }
                    data += '#';
                    endTime = System.currentTimeMillis();
                    data += (lasttime + endTime - startTime);
                    File f = new File("save.txt");
                    FileWriter fileWritter = null;
                    try {
                        fileWritter = new FileWriter(f.getName(), false);
                        fileWritter.write(data);
                        fileWritter.close();
                    } catch (IOException e) {
                        System.exit(1);
                    }
                    System.exit(0);
                    //end and save
                }
            }
            if (c == '6') {
                c = getnextChar();
                if (c == '1') {
                    c = getnextChar();
                    if (c == 'b' || c == 'B') {
                        health += 1;
                    }
                }
            }
            p = update(c, p);
            drawHealth(health, p);
            if (health == 0) {
                drawGameOver();
                System.exit(0);
            }
            if (p.x == doorLoc.x && p.y == doorLoc.y) {
                drawGameOverWin();
                System.exit(0);
            }
            TETile[][] hardWorld = becomeHard(world, p);
            if (!hardMode) {
                ter.renderFrame(world);
            } else {
                ter.renderFrame(hardWorld);
            }
        }
    }

    private TETile[][] newWorld() { //input needs to be N"number"S
        TETile[][] finalWorldFrame = null;
        finalWorldFrame = new TETile[Engine.WIDTH][Engine.HEIGHT];
        int height = finalWorldFrame[0].length;
        int width = finalWorldFrame.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }

        List<Room> rooms = new ArrayList<>();
        int roomNumber = rand.nextInt(5) + 20;
        for (int i = 0; i < roomNumber; i++) {
            rooms.add(new Room(finalWorldFrame, rand));
        }
        for (int i = 1; i < roomNumber; i++) {
            int rd1 = rand.nextInt(i);
            Room r1 = rooms.get(rd1);
            Room r2 = rooms.get(i);
            if (!r1.connectRoom(rand, r2, finalWorldFrame)) {
                i--;
                continue;
            }
            for (Room r: rooms) {
                r.redraw(finalWorldFrame);
            }
        }

        addWall(finalWorldFrame, Engine.WIDTH, Engine.HEIGHT);
        flower2floor(finalWorldFrame, Engine.WIDTH, Engine.HEIGHT);
        for (int i = 0; i < Engine.HEARTSNUM; i++) {
            int x = this.rand.nextInt(Engine.WIDTH);
            int y = this.rand.nextInt(Engine.HEIGHT);
            if (finalWorldFrame[x][y] != Tileset.FLOOR) {
                i--;
                continue;
            } else {
                finalWorldFrame[x][y] = Tileset.HEART;
            }
        }
        return addPortal(addDoor(finalWorldFrame));
    }
    TETile[][] addDoor(TETile[][] world) {
        while (true) {
            int i = rand.nextInt(WIDTH - 1);
            int j = rand.nextInt(HEIGHT - 1);
            if (world[i][j] == Tileset.WALL) {
                if (world[i - 1][j] == Tileset.WALL && world[i + 1][j] == Tileset.WALL && world[i][j - 1] == Tileset.FLOOR && world[i][j + 1] == Tileset.NOTHING) {
                    world[i][j] = Tileset.UNLOCKED_DOOR;
                    doorLoc = new Point(i, j);
                    break;
                }
                if (world[i - 1][j] == Tileset.WALL && world[i + 1][j] == Tileset.WALL && world[i][j - 1] == Tileset.NOTHING && world[i][j + 1] == Tileset.FLOOR) {
                    world[i][j] = Tileset.UNLOCKED_DOOR;
                    doorLoc = new Point(i, j);
                    break;
                }
                if (world[i][j - 1] == Tileset.WALL && world[i][j + 1] == Tileset.WALL && world[i - 1][j] == Tileset.FLOOR && world[i + 1][j] == Tileset.NOTHING) {
                    world[i][j] = Tileset.UNLOCKED_DOOR;
                    doorLoc = new Point(i, j);
                    break;
                }
                if (world[i][j - 1] == Tileset.WALL && world[i][j + 1] == Tileset.WALL && world[i - 1][j] == Tileset.NOTHING && world[i + 1][j] == Tileset.FLOOR) {
                    world[i][j] = Tileset.UNLOCKED_DOOR;
                    doorLoc = new Point(i, j);
                    break;
                }
            }
        }
        return world;
    }

    TETile[][] addPortal(TETile[][] world) {
        for (int i = 0; i < Engine.PORTALNUM; i++) {
            int x = this.rand.nextInt(Engine.WIDTH);
            int y = this.rand.nextInt(Engine.HEIGHT);
            if (world[x][y] != Tileset.FLOOR) {
                i--;
                continue;
            } else {
                world[x][y] = Tileset.PORTAL;
            }
        }
        return world;
    }
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void drawFrameHome() {
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3, "CS61B: THE GAME");
        font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 7, "New Game(N)");
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 8, "Load Game(L)");
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 9, "Quit (Q)");
        StdDraw.show();
    }

    private long drawGetSeed() {
        StdDraw.clear(StdDraw.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3, "Please enter a seed, enter s to end.");
        StdDraw.show();
        char c;
        String s = "";
        while (true) {
            c = getnextChar();
            if (c == 's' || c == 'S') {
                s = "N" + s + Character.toString(c);
                break;
            }
            s = s + Character.toString(c);
            StdDraw.clear(StdDraw.BLACK);
            font = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3, "Please enter a seed, enter s to end.");
            font = new Font("Monaco", Font.BOLD, 17);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 7, s);
            StdDraw.show();
        }
        return getSeed(s);
    }

    private String getNLQ() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
        char c = StdDraw.nextKeyTyped();
        return Character.toString(c);
    }

    public long getSeed() {
        String ret = "N";
        while (true) {
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    break;
                }
            }
            char c = StdDraw.nextKeyTyped();
            ret += c;
            if (c == 's' || c == 'S') {
                break;
            }
        }
        String s = "";
        for (int i = 1; i < ret.length() - 1; i++) {
            s += ret.charAt(i);
        }
        Long se = Long.parseLong(s);
        return se;
    }

    public Point update(char c, Point cur) {
        if (c == 'w' || c == 'W') {
            if (world[cur.x][cur.y + 1] == Tileset.PORTAL) {
                int i;
                for (i = 0; i < this.Portals.size(); i++) {
                    if (this.Portals.get(i).x == cur.x && this.Portals.get(i).y == cur.y + 1) {
                        break;
                    }
                }
                i = Engine.PORTALNUM - 1 - i;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                world[this.Portals.get(i).x][this.Portals.get(i).y] = Tileset.AVATAR;
                return new Point(this.Portals.get(i).x, this.Portals.get(i).y);
            }
            if (world[cur.x][cur.y + 1] == Tileset.HEART) {
                world[cur.x][cur.y + 1] = Tileset.AVATAR;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                health += 1;
                drawNewHeart();
                return new Point(cur.x, cur.y + 1);
            }
            if (world[cur.x][cur.y + 1] != Tileset.WALL) {
                world[cur.x][cur.y + 1] = Tileset.AVATAR;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                return new Point(cur.x, cur.y + 1);
            } else {
                if (health > 1) {
                    drawHitwall();
                }
                health -= 1;
            }
        }
        if (c == 's' || c == 'S') {
            if (world[cur.x][cur.y - 1] == Tileset.PORTAL) {
                int i;
                for (i = 0; i < this.Portals.size(); i++) {
                    if (this.Portals.get(i).x == cur.x && this.Portals.get(i).y == cur.y - 1) {
                        break;
                    }
                }
                i = Engine.PORTALNUM - 1 - i;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                world[this.Portals.get(i).x][this.Portals.get(i).y] = Tileset.AVATAR;
                return new Point(this.Portals.get(i).x, this.Portals.get(i).y);
            }
            if (world[cur.x][cur.y - 1] == Tileset.HEART) {
                world[cur.x][cur.y - 1] = Tileset.AVATAR;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                health += 1;
                drawNewHeart();
                return new Point(cur.x, cur.y - 1);
            }
            if (world[cur.x][cur.y - 1] != Tileset.WALL) {
                world[cur.x][cur.y - 1] = Tileset.AVATAR;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                return new Point(cur.x, cur.y - 1);
            } else {
                if (health > 1) {
                    drawHitwall();
                }
                health -= 1;
            }
        }
        if (c == 'a' || c == 'A') {
            if (world[cur.x - 1][cur.y] == Tileset.PORTAL) {
                int i;
                for (i = 0; i < this.Portals.size(); i++) {
                    if (this.Portals.get(i).x == cur.x - 1&& this.Portals.get(i).y == cur.y) {
                        break;
                    }
                }
                i = Engine.PORTALNUM - 1 - i;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                world[this.Portals.get(i).x][this.Portals.get(i).y] = Tileset.AVATAR;
                return new Point(this.Portals.get(i).x, this.Portals.get(i).y);
            }
            if (world[cur.x - 1][cur.y] == Tileset.HEART) {
                world[cur.x - 1][cur.y] = Tileset.AVATAR;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                health -= 1;
                drawNewHeart();
                return new Point(cur.x - 1, cur.y);
            }
            if (world[cur.x - 1][cur.y] != Tileset.WALL) {
                world[cur.x - 1][cur.y] = Tileset.AVATAR;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                return new Point(cur.x - 1, cur.y);
            } else {
                if (health > 1) {
                    drawHitwall();
                }
                health -= 1;
            }
        }
        if (c == 'd' || c == 'D') {
            if (world[cur.x + 1][cur.y] == Tileset.PORTAL) {
                int i;
                for (i = 0; i < this.Portals.size(); i++) {
                    if (this.Portals.get(i).x == cur.x + 1 && this.Portals.get(i).y == cur.y) {
                        break;
                    }
                }
                i = Engine.PORTALNUM - 1 - i;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                world[this.Portals.get(i).x][this.Portals.get(i).y] = Tileset.AVATAR;
                return new Point(this.Portals.get(i).x, this.Portals.get(i).y);
            }
            if (world[cur.x + 1][cur.y] == Tileset.HEART) {
                world[cur.x + 1][cur.y] = Tileset.AVATAR;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                health += 1;
                drawNewHeart();
                return new Point(cur.x + 1, cur.y);
            }
            if (world[cur.x + 1][cur.y] != Tileset.WALL) {
                world[cur.x + 1][cur.y] = Tileset.AVATAR;
                int n;
                for (n = 0; n < this.Portals.size(); n++) {
                    if (this.Portals.get(n).x == cur.x && this.Portals.get(n).y == cur.y) {
                        world[cur.x][cur.y] = Tileset.PORTAL;
                        break;
                    }
                }
                if (n == this.Portals.size()) {
                    world[cur.x][cur.y] = Tileset.FLOOR;
                }
                return new Point(cur.x + 1, cur.y);
            } else {
                if (health > 1) {
                    drawHitwall();
                }
                health -= 1;
            }

        }
        return cur;
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        Point p = null;
        String stringBeginWithNLQ = getStringBeginWithNLQ(input);
        String nlq = Character.toString(stringBeginWithNLQ.charAt(0));
        String operations = null;
        if (nlq.equals("N") || nlq.equals("n")) {
            this.seed = getSeed(stringBeginWithNLQ);
            this.rand = new Random(this.seed);
            this.world = newWorld();
            p = getAvator();
            operations = stringBeginWithNLQ.substring(1);
        } else if (nlq.equals("L") || nlq.equals("l")) {
            FileReader fr = null;
            try {
                fr = new FileReader("save.txt");
            } catch (IOException e) {
                System.exit(1);
            }
            int ch;
            String str = "";
            try {
                while ((ch = fr.read()) != -1) {
                    str += (char) ch;
                }
                //System.out.println(str);
                fr.close();
            } catch (IOException e) {
                System.exit(1);
            }
            String[] ans = str.split("#");
            long newSeed = Long.parseLong(ans[0]);
            this.seed = newSeed;
            this.rand = new Random(this.seed);
            int newX = Integer.parseInt(ans[1]);
            int newY = Integer.parseInt(ans[2]);
            this.world = newWorld();
            p = new Point(newX, newY);
            world[newX][newY] = Tileset.AVATAR;
            //System.out.println(p.x);
            operations = stringBeginWithNLQ.substring(1);
        } else {
            System.exit(0);
        }
        gaming(p, operations);
        return this.world;
    }

    private void gaming(Point p, String operations) {
        for (int i = 0; i < operations.length(); i++) {
            char c = operations.charAt(i);
            System.out.println(c);
            if (c == ':') {
                i++;
                c = operations.charAt(i);
                if (c == 'q' || c == 'Q') {
                    String data = "";
                    data += seed;
                    data += '#';
                    data += p.x;
                    data += '#';
                    data += p.y;
                    File f = new File("save.txt");
                    FileWriter fileWritter = null;
                    try {
                        fileWritter = new FileWriter(f.getName(), false);
                        fileWritter.write(data);
                        fileWritter.close();
                    } catch (IOException e) {
                        System.exit(1);
                    }
                    return;
                    //end and save
                } else {
                    i--;
                    continue;
                }
            }
            p = update(c, p);
            if (world[p.x][p.y] == Tileset.UNLOCKED_DOOR) {
                break;
            }
        }
    }

    public long getSeed(String stringBeginWithN) {
        String ret = "";
        for (int index = 0; index < stringBeginWithN.length(); index++) {
            char c = stringBeginWithN.charAt(index);
            ret += c;
            if (c == 's' || c == 'S') {
                break;
            }
        }
        String s = "";
        for (int i = 1; i < ret.length() - 1; i++) {
            s += ret.charAt(i);
        }
        Long se = Long.parseLong(s);
        return se;
    }

    private String getStringBeginWithNLQ(String input) {
        int index = 0;
        while (true) {
            if (input.charAt(index) == 'N' || input.charAt(index) == 'n') {
                break;
            } else if (input.charAt(index) == 'L' || input.charAt(index) == 'l') {
                break;
            } else if (input.charAt(index) == 'Q' || input.charAt(index) == 'q') {
                break;
            }
        }
        return input.substring(index);
    }


    public void addWall(TETile[][] w, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (w[i][j] == Tileset.FLOOR || w[i][j] == Tileset.FLOWER) {
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (!(k == 1 && l == 1)) {
                                if (w[i + k - 1][j + l - 1] == Tileset.NOTHING) {
                                    w[i + k - 1][j + l - 1] = Tileset.WALL;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void flower2floor(TETile[][] w, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (w[i][j] == Tileset.FLOWER) {
                    w[i][j] = Tileset.FLOOR;
                }
            }
        }
    }
    private void drawHealth(int health, Point p) {
        for (int i = 60; i < WIDTH; i++) {
            world[i][HEIGHT - 1] = Tileset.NOTHING;
        }
        for (int i = 60; i < 60 + health; i++) {
            world[i][HEIGHT - 1] = Tileset.TREE;
        }
        TETile[][] hardWorld = becomeHard(world, p);
        if (!hardMode) {
            ter.renderFrame(world);
        } else {
            ter.renderFrame(hardWorld);
        }
    }
    private void drawGameOver() {
        while (true) {
            Font font = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3, "CS61B: THE GAME");
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 5, "Press any key to exit");
            StdDraw.show();
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
    }
    private void drawTime() {
        Date now=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy/MM/dd E kk/mm", Locale.ENGLISH);
        for (int i = 20; i < 40; i++) {
            world[i][HEIGHT - 1] = Tileset.NOTHING;
        }
        for (int i = 20; i < 20 + f.format(now).length(); i++) {
            world[i][HEIGHT - 1] = new TETile(f.format(now).charAt(i-20),Color.PINK, Color.black, "time");
        }
    }
    private void drawHUD(Point p) {
        while (true) {
            drawTime();
            String str;
            if (StdDraw.mouseY() <= 0 || StdDraw.mouseY() >= HEIGHT) {
                str = "nothing";
            } else {
                if (hardMode) {
                    str = becomeHard(this.world, p)[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
                } else {
                    str = this.world[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
                }

            }
            for (int i = 0; i < str.length(); i++) {
                int hh = HEIGHT - 1;
                world[i][hh] = new TETile(str.toCharArray()[i], Color.PINK, Color.black, "nothing");
            }
            TETile[][] hardWorld = becomeHard(world, p);
            if (!hardMode) {
                ter.renderFrame(world);
            } else {
                ter.renderFrame(hardWorld);
            }
            for (int i = 0; i < str.length(); i++) {
                world[i][HEIGHT - 1] = Tileset.NOTHING;
            }
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
    }

    private void drawHitwall() {
        StdDraw.clear(StdDraw.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3, "Careful, you are hurting yourself.");
        font = new Font("Monaco", Font.BOLD, 17);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 7, "Only " + (this.health - 1) + " lives left.");
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 9, "Press any key to continue");
        StdDraw.show();
        getnextChar();
    }

    private void drawNewHeart() {
        StdDraw.clear(StdDraw.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3, "You have another lifeeeeeee!");
        font = new Font("Monaco", Font.BOLD, 17);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 7, (this.health) + " lives left.");
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 9, "Press any key to continue");
        StdDraw.show();
        getnextChar();
    }

    private void drawChooseMode() {
        StdDraw.clear(StdDraw.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 + 5, "GAME MODE");
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 2, "press E for easy     ");
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 4, "press D for difficult");
        StdDraw.show();
        char c;
        while (true) {
            c = getnextChar();
            if (c == 'e' || c == 'E') {
                hardMode = false;
                break;
            }
            if (c == 'd' || c == 'D') {
                hardMode = true;
                break;
            }
        }

    }

    TETile[][] becomeHard(TETile[][] world, Point cur) {
        TETile[][] hardWorld = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (j == HEIGHT - 1) {
                    hardWorld[i][j] = world[i][j];
                } else {
                    if (Math.abs(cur.x - i) + Math.abs(cur.y - j) > 5) {
                        hardWorld[i][j] = Tileset.NOTHING;
                    } else {
                        hardWorld[i][j] = world[i][j];
                    }
                }
            }
        }
        return hardWorld;
    }

    void drawGameOverWin() {
        endTime = System.currentTimeMillis();
        long score = (long)(10000 / Math.log(endTime - startTime + lasttime) + 500 * health);
        String data = "";
        data += score;
        data += "#";
        File f = new File("rank.txt");
        FileWriter fileWritter = null;
        try {
            fileWritter = new FileWriter(f.getName(), true);
            fileWritter.write(data);
            fileWritter.close();
        } catch (IOException e) {
            System.exit(1);
        }
        FileReader fr = null;
        try {
            fr = new FileReader("rank.txt");
        } catch (IOException e) {
            System.exit(1);
        }
        int ch;
        String str = "";
        try {
            while ((ch = fr.read()) != -1) {
                str += (char) ch;
            }
        } catch (IOException e) {
            System.exit(1);
        }
        try {
            fr.close();
        } catch (IOException e) {
            System.exit(1);
        }
        String[] ans = str.split("#");
        long[] rank = new long[10000];
        int cur = 0;
        for (int i = 0; i < ans.length; i++) {
            rank[i] = Long.parseLong(ans[i]);
            cur++;
        }
        Arrays.sort(rank);
        for (int i = 0; i < rank.length/2; i ++) {
            long temp = rank[i];
            rank[i] = rank[rank.length - i - 1];
            rank[rank.length - i - 1] = temp;
        }


        int yourPlace = 10000;
        for (int i = 0; i < cur; i++) {
            if (rank[i] == score) {
                yourPlace = i;
                break;
            }
        }
        while (true) {
            StdDraw.clear(Color.BLACK);
            Font font = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3, "You Win!!!");
            StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 2, "Your score:" + score);
            StdDraw.setFont(font);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3 - 5, "Press any key to exit");
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.text(WIDTH / 2 + 22, HEIGHT * 2 / 3 + 2, "Rank");
            StdDraw.setPenColor(StdDraw.YELLOW);
            for (int i = 0; i < Math.min(5, cur); i ++) {
                font = new Font("Monaco", Font.CENTER_BASELINE, 10);
                StdDraw.text(WIDTH / 2 + 25, HEIGHT * 2 / 3 - i * 2, String.valueOf(rank[i]));
                if (yourPlace == i) {
                    StdDraw.text(WIDTH / 2 + 29, HEIGHT * 2 / 3 - i * 2, "You");
                }
            }
            StdDraw.show();
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
    }


}
