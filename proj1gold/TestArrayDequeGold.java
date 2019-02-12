import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testStdAD() {
        StudentArrayDeque<Integer> stdAD = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> stfAD = new ArrayDequeSolution<>();
        String msg = "";
        int input;
        Integer std;
        Integer stf;
        int size = 0;
        int roll;
        while (true) {
            roll = StdRandom.uniform(4);
            if (roll == 0) {
                input = StdRandom.uniform(100);
                msg = msg + "\naddFirst(" + input + ")";
                stdAD.addFirst(input);
                stfAD.addFirst(input);
                size++;
            } else if (roll == 1) {
                input = StdRandom.uniform(100);
                msg = msg + "\naddLast(" + input + ")";
                stdAD.addLast(input);
                stfAD.addLast(input);
                size++;
            } else if (roll == 2) {
                if (size != 0) {
                    msg = msg + "\nremoveFirst()";
                    assertEquals(msg, stfAD.removeFirst(), stdAD.removeFirst());
                    size--;
                }
            } else {
                if (size != 0) {
                    msg = msg + "\nremoveLast()";
                    assertEquals(msg, stfAD.removeLast(), stdAD.removeLast());
                    size--;
                }
            }
        }
    }
}
