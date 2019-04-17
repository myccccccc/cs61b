package bearmaps.lab9;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class MyTrieSet implements TrieSet61B{
    private boolean isKey;
    private Map<Character, MyTrieSet> links;

    public MyTrieSet() {
        links = new HashMap<>();
        isKey = false;
    }

    @Override
    /** Clears all items out of Trie */
    public void clear() {
        links = new HashMap<>();
    }

    private MyTrieSet get(Character c) {
        return links.get(c);
    }

    private boolean containsHelper(MyTrieSet node, String key, int index) {
        if (node.get(key.charAt(index)) == null) {
            return false;
        }
        else if (index == (key.length() - 1)) {
            return node.get(key.charAt(index)).isKey;
        }
        else {
            return containsHelper(node.get(key.charAt(index)), key, index + 1);
        }
    }

    @Override
    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            return false;
        }
        return containsHelper(this, key, 0);
    }

    private void addHelper(MyTrieSet node, String key, int index) {
        if (index == key.length()) {
            node.isKey = true;
            return;
        }
        if (node.get(key.charAt(index)) == null) {
            while (index != key.length()) {
                MyTrieSet newNode = new MyTrieSet();
                node.links.put(key.charAt(index), newNode);
                node = newNode;
                index++;
            }
            node.isKey = true;
        }
        else {
            addHelper(node.get(key.charAt(index)), key, index + 1);
        }
    }

    @Override
    /** Inserts string KEY into Trie */
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        if (contains(key)) {
            return;
        }
        addHelper(this, key, 0);
    }

    private void keysWithPrefixHelper(List<String> l, String s, MyTrieSet node) {
        if(node.isKey) {
            l.add(s);
        }
        for(Character c : node.links.keySet()) {
            keysWithPrefixHelper(l, s + c, node.links.get(c));
        }
    }

    @Override
    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix) {
        List<String> l = new ArrayList<>();
        if (prefix == null || prefix.length() < 1) {
            return l;
        }
        MyTrieSet node = this;
        for (int i = 0; i < prefix.length(); i++) {
            node = node.get(prefix.charAt(i));
            if (node == null) {
                return l;
            }
        }
        keysWithPrefixHelper(l, prefix, node);
        return l;
    }

    @Override
    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
