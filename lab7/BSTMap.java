import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private BSTMap<K, V> left;
    private BSTMap<K, V> right;
    private K key;
    private V value;
    int size;

    public BSTMap() {
        size = 0;
        left = null;
        right = null;
        key = null;
        value = null;
    }

    private BSTMap<K, V> find (BSTMap<K, V> t, K k) {
        if (t == null) {
            return t;
        }
        else if (t.key.compareTo(k) == 0) {
            return t;
        }
        else if (t.key.compareTo(k) > 0) {
            return find(t.left, k);
        }
        else {
            return find(t.right, k);
        }
    }

    private BSTMap<K, V> insert(BSTMap<K, V> t, K k, V v) {
        if (t == null) {
            t = new BSTMap<K, V>();
            t.put(k, v);
        }
        else if (t.key.compareTo(k) == 0) {
            t.size++;
            t.value = v;
        }
        else if (t.key.compareTo(k) > 0) {
            t.size++;
            t.left = insert(t.left, k, v);
        }
        else {
            t.size++;
            t.right = insert(t.right, k, v);
        }
        return t;
    }

    @Override
    /** Removes all of the mappings from this map. */
    public void clear() {
        size = 0;
        key = null;
        value = null;
        left = null;
        right = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (size() == 0) {
            return false;
        }
        return find(this, key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (size() == 0) {
            return null;
        }
        BSTMap<K, V> m = find(this, key);
        if (m == null) {
            return null;
        }
        return m.value;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public  int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (size == 0) {
            this.key = key;
            this.value = value;
            size++;
        }
        else {
            insert(this, key, value);
        }

    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    public static void main(String[] ar) {
        BSTMap<String, String> a = new BSTMap<>();
    }
}
