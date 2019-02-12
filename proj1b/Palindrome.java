public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        if (word == null) {
            return null;
        }
        Deque<Character> a = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            /* @source
            /*https://stackoverflow.com/questions/11229986/get-string-character-by-index-java*/
            a.addLast(word.charAt(i));
        }
        return a;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> a = wordToDeque(word);
        if (a.isEmpty() || a.size() == 1) {
            return true;
        }
        while (a.removeFirst() == a.removeLast()) {
            if (a.isEmpty() || a.size() == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> a = wordToDeque(word);
        if (a.isEmpty() || a.size() == 1) {
            return true;
        }
        while (cc.equalChars(a.removeFirst(), a.removeLast())) {
            if (a.isEmpty() || a.size() == 1) {
                return true;
            }
        }
        return false;
    }

}
