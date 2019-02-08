public class ArrayDeque<T> {
    private T[] item;
    private int size;
    private int head;
    private int tail;
    private int FACTOR = 2;
    private int STARTINGSIZE = 8;
    private float threshold = (float) 1 / 4;

    public ArrayDeque() {
        item = (T[]) new Object[STARTINGSIZE];
        size = 0;
        head = -1;
        tail = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        item = (T[]) new Object[STARTINGSIZE];
        size = 0;
        head = -1;
        tail = 0;
        int index = 0;
        while (index < other.size()) {
            this.addLast((T) other.get(index));
            index++;
        }
    }
    private int getPositive(int a) {
        while (a < 0) {
            a = a + this.item.length;
        }
        return a;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T get(int index) {
        if (index >= size() || index < 0) {
            return null;
        }
        return item[getPositive(index + head + 1) % this.item.length];
    }

    public void printDeque() {
        int index = 0;
        while (index < size()) {
            System.out.println(get(index) + " ");
            index++;
        }
        System.out.println();
    }

    private void newarray(int s) {
        T[] p = (T[]) new Object[s];
        for (int index = 0; index < size(); index++) {
            p[index] = get(index);
        }
        head = -1;
        tail = size();
        this.item = p;
    }
    public void addFirst(T i) {
        if (size + 1 > this.item.length) {
            newarray(this.item.length * FACTOR);
        }
        this.item[getPositive(head) % this.item.length] = i;
        head--;
        size++;
    }

    public void addLast(T i) {
        if (size + 1 > this.item.length) {
            newarray(this.item.length * FACTOR);
        }
        this.item[getPositive(tail) % this.item.length] = i;
        tail++;
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T i = get(0);
        this.item[getPositive(head + 1) % this.item.length] = null;
        head++;
        size--;
        if ((float) size() / this.item.length < threshold) {
            if (this.item.length == STARTINGSIZE) {
                return i;
            }
            newarray(this.item.length / FACTOR);
        }
        return i;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T i = get(size() - 1);
        this.item[getPositive(tail - 1) % this.item.length] = null;
        tail--;
        size--;
        if ((float) size() / this.item.length < threshold) {
            if (this.item.length == STARTINGSIZE) {
                return i;
            }
            newarray(this.item.length / FACTOR);
        }
        return i;
    }



}
