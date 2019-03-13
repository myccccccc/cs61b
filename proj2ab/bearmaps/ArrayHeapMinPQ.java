package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<PriorityNode> pns;
    private Map<PriorityNode, Integer> pn2index;

    public ArrayHeapMinPQ() {
        pns = new ArrayList<>();
        pn2index = new HashMap<>();
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        PriorityNode newNode = new PriorityNode(item, priority);
        pns.add(newNode);
        pn2index.put(newNode, pns.size() - 1);
        moveUp(pns.size() - 1);
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return pn2index.containsKey(new PriorityNode(item, 0));
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (pns.size() == 0) {
            throw new NoSuchElementException();
        }
        return pns.get(0).getItem();
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (pns.size() == 0) {
            throw new NoSuchElementException();
        }
        PriorityNode first = pns.get(0);
        PriorityNode last = pns.get(pns.size() - 1);
        pns.set(0, last);
        pns.remove(pns.size() - 1);
        pn2index.put(last, 0);
        pn2index.remove(first);
        if (pns.size() == 0) {
            return first.getItem();
        }
        moveDown(0);
        return first.getItem();
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        Integer index = pn2index.get(new PriorityNode(item, 0));
        if (index == null) {
            throw new NoSuchElementException();
        }
        double oldpriority = pns.get(index).getPriority();
        pns.get(index).setPriority(priority);
        if (priority > oldpriority) {
            moveDown(index);
        } else if (priority < oldpriority) {
            moveUp(index);
        }
        return;
    }

    private void swap(int index1, int index2) {
        PriorityNode p1 = pns.get(index1);
        PriorityNode p2 = pns.get(index2);
        pns.set(index1, p2);
        pns.set(index2, p1);
        pn2index.put(p1, index2);
        pn2index.put(p2, index1);
    }

    private void moveUp(int index) {
        int parent = (index - 1) / 2;
        if (parent < 0) {
            return;
        } else if (pns.get(parent).getPriority() <= pns.get(index).getPriority()) {
            return;
        } else {
            swap(parent, index);
            moveUp(parent);
        }
    }

    private void moveDown(int index) {
        int leftchild = index * 2 + 1;
        int rightchild = index * 2 + 2;
        int smallestIndex = index;
        double smallestPriority = pns.get(index).getPriority();
        if (leftchild < pns.size() && pns.get(leftchild).getPriority() < smallestPriority) {
            smallestIndex = leftchild;
            smallestPriority = pns.get(leftchild).getPriority();
        }
        if (rightchild < pns.size() && pns.get(rightchild).getPriority() < smallestPriority) {
            smallestIndex = rightchild;
            smallestPriority = pns.get(rightchild).getPriority();
        }
        if (smallestIndex != index) {
            swap(smallestIndex, index);
            moveDown(smallestIndex);
        }
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return pns.size();
    }



    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
