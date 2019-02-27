package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    /* Variable for the capacity. */
    private int capacity;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        this.rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = (last + 1) % capacity();
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T returnitem = rb[first];
        rb[first] = null;
        first = (first + 1) % capacity();
        fillCount--;
        return returnitem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int index;
        ArrayRingBufferIterator() {
            index = 0;
        }
        @Override
        public boolean hasNext() {
            return !isEmpty();
        }

        @Override
        public T next() {
            index++;
            return rb[(first + index - 1) % capacity()];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> a = (ArrayRingBuffer<T>) o;
        if (a.fillCount() != this.fillCount()) {
            return false;
        }
        int c = 0;
        for (T x : a) {
            if (!x.equals(this.rb[(first + c) % capacity()])) {
                return false;
            }
        }
        return true;
    }

}

