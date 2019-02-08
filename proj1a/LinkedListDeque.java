public class LinkedListDeque<T> {
    public class Node {
        private Node prev;
        private Node next;
        private T item;
        public Node(T a, Node p, Node n) {
            prev = p;
            next = n;
            item = a;
        }
        /* use this when first create the Deque*/
        public Node() {
            prev = null;
            next = null;
            item = null;
        }
    }

    private Node sentinelf;
    private Node sentinell;
    private int size;

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public LinkedListDeque() {
        sentinelf = new Node();
        sentinell = new Node();
        sentinelf.next = sentinell;
        sentinell.prev = sentinelf;
        size = 0;
    }

    public LinkedListDeque( LinkedListDeque l) {
        sentinelf = new Node();
        sentinell = new Node();
        sentinelf.next = sentinell;
        sentinell.prev = sentinelf;
        size = 0;
        int i = 0;
        while (i < l.size()) {
            addLast((T) l.get(i)); /* @source https://www.youtube.com/watch?v=JNroRiEG7U4 */
            i++;
        }
    }

    public void addFirst(T item) {
        sentinelf.next = new Node(item, sentinelf, sentinelf.next);
        sentinelf.next.next.prev = sentinelf.next;
        size = size + 1;
    }

    public void addLast(T item) {
        sentinell.prev = new Node(item, sentinell.prev, sentinell);
        sentinell.prev.prev.next = sentinell.prev;
        size = size + 1;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = sentinelf.next;
        while (p != sentinell) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty() == true) {
            return null;
        }
        T item = sentinelf.next.item;
        sentinelf.next.next.prev = sentinelf;
        sentinelf.next = sentinelf.next.next;
        size--;
        return item;
    }

    public T removeLast() {
        if (isEmpty() == true) {
            return null;
        }
        T item = sentinell.prev.item;
        sentinell.prev.prev.next = sentinell;
        sentinell.prev = sentinell.prev.prev;
        size--;
        return item;
    }

    public T get(int index) {
        if (index >= size()) {
            return null;
        }
        Node p = sentinelf.next;
        while (index > 0) {
            p = p.next;
            index = index -1;
        }
        return p.item;
    }

    private T Recursive(int index, Node p) {
        if (index == 0) {
            return p.item;
        }
        return Recursive(index - 1, p.next);
    }
    public T getRecursive(int index) {
        if (index >= size()) {
            return null;
        }
        return Recursive(index, sentinelf.next);
    }

}
