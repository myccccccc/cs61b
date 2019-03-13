package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void genericsTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        ArrayHeapMinPQ<Integer> b = new ArrayHeapMinPQ<>();
        ArrayHeapMinPQ<Boolean> c = new ArrayHeapMinPQ<>();
    }

    @Test
    public void addTest() {
        ArrayHeapMinPQ<String> b = new ArrayHeapMinPQ<>();
        assertFalse(b.contains("waterYouDoingHere"));
        for (int i = 0; i < 455; i++) {
            b.add("hi" + i, 454 - i);
        }
        for (int i = 0; i < 455; i++) {
            assertTrue(b.contains("hi" + i));
        }
        for (int i = 0; i < 455; i++) {
            assertEquals("hi" + (454 - i), b.getSmallest());
            assertEquals("hi" + (454 - i), b.removeSmallest());
        }
        assertEquals(0, b.size());

        ArrayHeapMinPQ<String> c = new ArrayHeapMinPQ<>();
        assertFalse(c.contains("waterYouDoingHere"));
        c.add("waterYouDoingHere", 0);
        c.removeSmallest();
        assertEquals(0, c.size());
        assertFalse(c.contains("waterYouDoingHere"));
    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<String> b = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 455; i++) {
            b.add("hi" + i, 454 - i);
        }
        b.changePriority("hi" + 99, -1);
        b.changePriority("hi" + 169, 1000);
        assertEquals("hi" + 99, b.removeSmallest());
        String s = "";
        while (b.size() != 0) {
            s = b.removeSmallest();
        }
        assertEquals("hi" + 169, s);
    }




}
