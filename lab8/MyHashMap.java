import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V>{

    private int initialSize;
    private double loadFactor;
    private int multiplyFacotr;
    private int n;
    private int size;
    private List<K>[] keys;
    private List<V>[] values;

    private void keysInit() {
        for(int i = 0; i < keys.length; i++) {
            keys[i] = new LinkedList<>();
        }
    }

    private void valuesInit() {
        for(int i = 0; i < values.length; i++) {
            values[i] = new LinkedList<>();
        }
    }

    public MyHashMap() {
        this.initialSize = 16;
        this.loadFactor = 0.75;
        this.multiplyFacotr = 2;
        this.size = initialSize;
        this.n = 0;
        values = new List[size];
        keys = new List[size];
        keysInit();
        valuesInit();
    }
    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        this.loadFactor = 0.75;
        this.multiplyFacotr = 2;
        this.size = initialSize;
        this.n = 0;
        values = new List[size];
        keys = new List[size];
        keysInit();
        valuesInit();
    }
    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.multiplyFacotr = 2;
        this.size = initialSize;
        this.n = 0;
        values = new List[size];
        keys = new List[size];
        keysInit();
        valuesInit();
    }

    @Override
    /** Removes all of the mappings from this map. */
    public void clear() {
        n = 0;
        values = new List[size];
        keys = new List[size];
        keysInit();
        valuesInit();
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (n == 0) {
            return false;
        }
        if (key == null) {
            return false;
        }
        for (K k : keys[Math.floorMod(key.hashCode(), size)]) {
            if (k.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (n == 0) {
            return null;
        }
        if (key == null) {
            return null;
        }
        int index = -1;
        for (K k : keys[Math.floorMod(key.hashCode(), size)]) {
            if (k.equals(key)) {
                index = keys[Math.floorMod(key.hashCode(), size)].indexOf(k);
            }
        }
        if (index == -1) {
            return null;
        }
        return values[Math.floorMod(key.hashCode(), size)].get(index);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public  int size() {
        return n;
    }

    private int putHelper(K key) {
        int index = -1;
        for (K k : keys[Math.floorMod(key.hashCode(), size)]) {
            if (k.equals(key)) {
                index = keys[Math.floorMod(key.hashCode(), size)].indexOf(k);
            }
        }
        return index;
    }

    private void resize() {
        List<K>[] oldkeys = keys;
        List<V>[] oldvalues = values;
        size = size * multiplyFacotr;
        keys = new List[size];
        values = new List[size];
        keysInit();
        valuesInit();
        for(List<K> lk : oldkeys) {
            int index = 0;
            for (K k: lk) {
                keys[Math.floorMod(k.hashCode(), size)].add(k);
                V v = oldvalues[Math.floorMod(k.hashCode(), size / multiplyFacotr)].get(index);
                values[Math.floorMod(k.hashCode(), size)].add(v);
                index++;
            }
        }
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int index = putHelper(key);
        if(index == -1) {
            keys[Math.floorMod(key.hashCode(), size)].add(key);
            values[Math.floorMod(key.hashCode(), size)].add(value);
            n++;
        }
        else {
            values[Math.floorMod(key.hashCode(), size)].set(index, value);
        }
        if ((double) n / size >= loadFactor) {
            resize();
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for(List<K> lk : keys) {
            for (K k: lk) {
                keySet.add(k);
            }
        }
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}