package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @Test
    public void rt() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> b = new NaiveMinPQ<>();
        List<Integer> l = new ArrayList<>();
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            int action = StdRandom.uniform(2);
            if (action == 0) {
                int num = StdRandom.uniform(90000);
                int priority = StdRandom.uniform(10000);
                try {
                    a.add(num, priority);
                } catch (IllegalArgumentException e) {
                    assertTrue(a.contains(num));
                    continue;
                }
                b.add(num, priority);
                l.add(num);
                m.put(num, priority);
            }
            if (action == 1) {
                if (l.size() == 0) {
                    continue;
                }
                int num = l.get(StdRandom.uniform(l.size()));
                //while (!l.contains(num)) {
                //    System.out.println("hi");
                //    num = l.get(StdRandom.uniform(l.size()));
                //}
                int priority = StdRandom.uniform(100);
                a.changePriority(num, priority);
                b.changePriority(num, priority);
                m.put(num, priority);
            }

        }
        while (a.size() != 0) {
            assertEquals(b.size(), a.size());
            Integer bt = b.getSmallest();
            Integer at = a.getSmallest();
            Integer bi = b.removeSmallest();
            Integer ai = a.removeSmallest();
            assertEquals(bt, bi);
            assertEquals(at, ai);
            assertEquals(m.get(bi), m.get(ai));
        }
    }

    @Test
    public void rtTime() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        //NaiveMinPQ<Integer> b = new NaiveMinPQ<>();
        List<Integer> l = new ArrayList<>();
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            int action = StdRandom.uniform(2);
            if (action == 0) {
                int num = StdRandom.uniform(90000);
                int priority = StdRandom.uniform(10000);
                try {
                    a.add(num, priority);
                } catch (IllegalArgumentException e) {
                    assertTrue(a.contains(num));
                    continue;
                }
                //b.add(num, priority);
                l.add(num);
                m.put(num, priority);
            }
            if (action == 1) {
                if (l.size() == 0) {
                    continue;
                }
                int num = l.get(StdRandom.uniform(l.size()));
                while (!l.contains(num)) {
                    num = l.get(StdRandom.uniform(l.size()));
                }
                int priority = StdRandom.uniform(100);
                a.changePriority(num, priority);
                //b.changePriority(num, priority);
                m.put(num, priority);
            }

        }
        while (a.size() != 0) {
            //assertEquals(b.size(), a.size());
            //Integer bt = b.getSmallest();
            Integer at = a.getSmallest();
            //Integer bi = b.removeSmallest();
            Integer ai = a.removeSmallest();
            //assertEquals(bt, bi);
            assertEquals(at, ai);
            //assertEquals(m.get(bi), m.get(ai));
        }
    }

    @Test
    public void rtTime2() {
        //ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> b = new NaiveMinPQ<>();
        List<Integer> l = new ArrayList<>();
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            int action = StdRandom.uniform(2);
            if (action == 0) {
                int num = StdRandom.uniform(90000);
                int priority = StdRandom.uniform(10000);
                if(!l.contains(num)) {
                    b.add(num, priority);
                }
                l.add(num);
                m.put(num, priority);
            }
            if (action == 1) {
                if (l.size() == 0) {
                    continue;
                }
                int num = l.get(StdRandom.uniform(l.size()));
                while (!l.contains(num)) {
                    num = l.get(StdRandom.uniform(l.size()));
                }
                int priority = StdRandom.uniform(100);
                b.changePriority(num, priority);
                m.put(num, priority);
            }

        }
        while (b.size() != 0) {
            //assertEquals(b.size(), a.size());
            Integer bt = b.getSmallest();
            //Integer at = a.getSmallest();
            Integer bi = b.removeSmallest();
            //Integer ai = a.removeSmallest();
            assertEquals(bt, bi);
            //assertEquals(at, ai);
            //assertEquals(m.get(bi), m.get(ai));
        }
    }

}
