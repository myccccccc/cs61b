import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     *
     * This method should take linear time.
     *
     * @param   items  A Queue of items.
     * @return         A Queue of queues, each containing an item from items.
     *
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> a = new Queue<>();
        for (Item b : items) {
            Queue<Item> g = new Queue<>();
            g.enqueue(b);
            a.enqueue(g);
        }
        return a;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> a = new Queue<>();
        while (q1.size() != 0 || q2.size() != 0) {
            a.enqueue(MergeSort.getMin(q1, q2));
        }
        return a;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     *
     * This method should take roughly nlogn time where n is the size of "items"
     * this method should be non-destructive and not empty "items".
     *
     * @param   items  A Queue to be sorted.
     * @return         A Queue containing every item in "items".
     *
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        Queue<Queue<Item>> whole = MergeSort.makeSingleItemQueues(items);
        while (whole.size() > 1) {
            Queue<Queue<Item>> tmp = new Queue<>();
            Queue<Item> t1;
            Queue<Item> t2;
            while (whole.size() != 0) {
                if (whole.size() >= 2) {
                    t1 = whole.dequeue();
                    t2 = whole.dequeue();
                    tmp.enqueue(MergeSort.mergeSortedQueues(t1,t2));
                }
                else {
                    tmp.enqueue(whole.dequeue());
                }
            }
            whole = tmp;
        }
        return whole.dequeue();
    }
}
