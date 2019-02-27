package es.datastructur.synthesizer;

import java.util.HashSet;
import java.util.Set;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayRingBuffer<>((int) (SR / Math.round(frequency)));
        while (!buffer.isFull()) {
            buffer.enqueue((double) 0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        /* clear the buffer and do nothing */
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        Set<Double> numPutin = new HashSet<>();
        while (numPutin.size() < buffer.capacity()) {
            numPutin.add(Math.random() - 0.5);
        }
        for (Double d : numPutin) {
            buffer.enqueue(d);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        Double a = buffer.dequeue();
        Double b = buffer.peek();
        buffer.enqueue((a + b) * DECAY / 2);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}

