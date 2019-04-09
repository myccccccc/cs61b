package bearmaps.hw4;
import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex>  {
    private ArrayHeapMinPQ<Vertex> pq;
    private Map<Vertex, Double> distTo;
    private Map<Vertex, Vertex> previousVertex;
    private List<Vertex> solution;
    private double solutionWeight;
    private SolverOutcome outcome;
    private int numStatesExplored;
    private double timeSpent;
    private Stopwatch sw;

    private void astar(AStarGraph<Vertex> input, Vertex end, double timeout) {
        while (pq.size() != 0) {
            timeSpent = sw.elapsedTime();
            /*if (timeSpent > timeout) {
                return;
            }*/
            Vertex s = pq.removeSmallest();
            numStatesExplored++;
            if (s.equals(end)) {
                return;
            }
            for (WeightedEdge<Vertex> e : input.neighbors(s)) {
                if (!distTo.containsKey(e.to())) {
                    distTo.put(e.to(), distTo.get(e.from()) + e.weight());
                    pq.add(e.to(), distTo.get(e.to()) + input.estimatedDistanceToGoal(e.to(), end));
                    previousVertex.put(e.to(), e.from());
                } else if ((distTo.get(e.from()) + e.weight()) < distTo.get(e.to())) {
                    distTo.put(e.to(), distTo.get(e.from()) + e.weight());
                    double tmp = distTo.get(e.to()) + input.estimatedDistanceToGoal(e.to(), end);
                    pq.changePriority(e.to(), tmp);
                    previousVertex.put(e.to(), e.from());
                }
            }
        }
    }

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        sw = new Stopwatch();
        pq = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        previousVertex = new HashMap<>();
        solution = new ArrayList<>();
        numStatesExplored = -1;

        distTo.put(start, 0.0);
        previousVertex.put(start, null);
        pq.add(start, distTo.get(start) + input.estimatedDistanceToGoal(start, end));
        astar(input, end, timeout);
        if (distTo.containsKey(end)) {
            solutionWeight = distTo.get(end);
            outcome = SolverOutcome.SOLVED;
            solution.add(end);
            Vertex t = end;
            while (previousVertex.get(t) != null) {
                solution.add(0, previousVertex.get(t));
                t = previousVertex.get(t);
            }
        } else {
            solutionWeight = 0;
            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
            } else {
                outcome = SolverOutcome.UNSOLVABLE;
            }
        }
        timeSpent = sw.elapsedTime();
    }
    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        return solutionWeight;
    }
    public int numStatesExplored() {
        return numStatesExplored;
    }
    public double explorationTime() {
        return timeSpent;
    }
}
