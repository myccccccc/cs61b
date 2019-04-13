import edu.princeton.cs.algs4.Queue;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        Queue<String> t = QuickSort.quickSort(tas);
        String a = t.dequeue();
        String b = null;
        while (t.size() != 0) {
            b = t.dequeue();
            assertTrue(a.compareTo(b) <= 0);
            a = b;
        }
    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        Queue<String> t = MergeSort.mergeSort(tas);
        String a = t.dequeue();
        String b = null;
        while (t.size() != 0) {
            b = t.dequeue();
            assertTrue(a.compareTo(b) <= 0);
            a = b;
        }
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
