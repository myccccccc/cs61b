package creatures;

import edu.princeton.cs.algs4.StdRandom;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.*;
public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates plip with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    public  Clorus() {
        this(1);
    }

    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g, b);
    }

    public void attack(Creature c) {
        energy = energy + c.energy();
    }

    public void move() {
        energy = energy - 0.03;

    }

    public void stay() {
        energy = energy - 0.01;

    }

    public Clorus replicate() {
        energy = energy / 2;
        return new Clorus(energy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        List<Direction> emptyNeighbors = new ArrayList<>();
        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.add(d);
            }
        }

        List<Direction> plipNeighbors = new ArrayList<>();
        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("plip")) {
                plipNeighbors.add(d);
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        else if (!plipNeighbors.isEmpty()) {
            return new Action(Action.ActionType.ATTACK, plipNeighbors.get(StdRandom.uniform(plipNeighbors.size())));
        }

        else if(energy() >= 1) {
            return new Action(Action.ActionType.REPLICATE, emptyNeighbors.get(StdRandom.uniform(emptyNeighbors.size())));
        }
        // Rule 4
        return new Action(Action.ActionType.MOVE, emptyNeighbors.get(StdRandom.uniform(emptyNeighbors.size())));
    }

}
