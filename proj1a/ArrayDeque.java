public class ArrayDeque <mytype> {
    private mytype[] item;
    private int size;
    private int head;
    private int tail;
    private int FACTOR = 2;
    private int STARTINGSIZE = 8;
    private float threshold = (float) 1 / 4;

    public ArrayDeque() {
        item = (mytype[]) new Object[STARTINGSIZE];
        size = 0;
        head = -1;
        tail = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        item = (mytype[]) new Object[STARTINGSIZE];
        size = 0;
        head = -1;
        tail = 0;
        int index = 0;
        while (index < other.size()) {
            this.addLast((mytype) other.get(index));
            index ++;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public mytype get(int index) {
        if(index >= size() || index < 0) {
            return null;
        }
        return item[(index + head + 1 + this.item.length) % this.item.length];
    }

    public void printDeque() {
        int index = 0;
        while (index < size()) {
            System.out.println(get(index) + " ");
            index ++;
        }
        System.out.println();
    }

    private void newarray(int s) {
        mytype[] p = (mytype[]) new Object[s];
        for(int index = 0; index < size(); index++) {
            p[index] = get(index);
        }
        head = -1;
        tail = size();
        this.item = p;
    }
    public void addFirst(mytype item) {
        if(size + 1 > this.item.length) {
            newarray(this.item.length * FACTOR);
        }
        this.item[(this.item.length + head) % this.item.length] = item;
        head --;
        size ++;
    }

    public void addLast(mytype item) {
        if(size + 1 > this.item.length) {
            newarray(this.item.length * FACTOR);
        }
        this.item[(this.item.length + tail) % this.item.length] = item;
        tail ++;
        size ++;
    }

    public mytype removeFirst() {
        if(isEmpty() == true) {
            return null;
        }
        mytype item = get(0);
        this.item[(head + 1 + this.item.length) % this.item.length] = null;
        head ++;
        size --;
        if((float)size()/this.item.length < threshold) {
            if(this.item.length == STARTINGSIZE ) {
                return item;
            }
            newarray(this.item.length / FACTOR);
        }
        return item;
    }

    public mytype removeLast() {
        if(isEmpty() == true) {
            return null;
        }
        mytype item = get(size() - 1);
        this.item[(tail - 1 + this.item.length) % this.item.length] = null;
        tail --;
        size --;
        if((float)size()/this.item.length < threshold) {
            if(this.item.length == STARTINGSIZE ) {
                return item;
            }
            newarray(this.item.length / FACTOR);
        }
        return item;
    }

}
