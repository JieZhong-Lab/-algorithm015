import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//127. 单词接龙
public class LadderLength {
    public int ladderLength_5(String beginWord, String endWord, List<String> wordList) {
        if (beginWord == null || endWord == null || wordList == null) return 0;
        
        Set<String> wordSet = new HashSet<>(wordList);
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();

        int len = beginWord.length();
        int cost = 1;
        Set<String> visited = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                Set<String> tmp = beginSet;
                beginSet = endSet;
                endSet = tmp;
            }
            Set<String> currLayer = new HashSet<>();
            for (String word : beginSet) {
                char[] currWord = word.toCharArray();
                for (int i = 0; i < len; i++) {
                    char origChar = currWord[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        currWord[i] = c;
                        String tmpWord = String.valueOf(currWord);

                        if (endSet.contains(tmpWord)) {
                            return cost + 1;
                        }
                        if (!visited.contains(tmpWord) && wordSet.contains(tmpWord)) {
                            currLayer.add(tmpWord);
                            visited.add(tmpWord);
                        }
                    }
                    currWord[i] = origChar;
                }
            }
            beginSet = currLayer;
            cost++;
        }
        return 0;
    }
    //双向广度优先搜索. 优化2 - 28ms
    public int ladderLength_4_2(String beginWord, String endWord, List<String> wordList) {
        if (beginWord == null || endWord == null || wordList == null || beginWord.equals(endWord)) return 0;
        
        int end = wordList.indexOf(endWord);
        if (end == -1) return 0;

        wordList.add(beginWord);

        Set<String> words = new HashSet<>(wordList);
        Queue<String> queue1 = new LinkedList<>();
        Queue<String> queue2 = new LinkedList<>();
        //记录是否已被访问过
        Set<String> visited1 = new HashSet<>();
        Set<String> visited2 = new HashSet<>();
        queue1.offer(beginWord);
        queue2.offer(endWord);
        visited1.add(beginWord);
        visited2.add(endWord);
        int cost = 0;

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            cost++;
            //每次遍历一层时，都从层结点少的那端遍历
            //设queue1始终为结点少的Queue
            if (queue1.size() > queue2.size()) {
                Queue<String> tmp = queue1;
                queue1 = queue2;
                queue2 = tmp;
                Set<String> tmpVisisted = visited1;
                visited1 = visited2;
                visited2 = tmpVisisted;
            }
            int size = queue1.size();
            while (size-- > 0) {
                String currWord = queue1.poll();
                //如果字典中单词数量很大，一个单词一个单词比的效率比较低; 用可能的单词去字典中查找（可能的单词数量相对较少）
                char[] currWordArr = currWord.toCharArray();
                for (int i = 0; i < currWord.length(); i++) {
                    char originChr = currWordArr[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        currWordArr[i] = c;
                        String newWord = String.valueOf(currWordArr);
                        
                        if (visited1.contains(newWord)) continue;
                        if (visited2.contains(newWord)) return cost + 1;
                        if (words.contains(newWord)) {
                            queue1.offer(newWord);
                            visited1.add(newWord);
                        }
                    }
                    currWordArr[i] = originChr;
                }
            }
        }
        return 0;
    }
    //双向广度优先搜索. 优化 - 141ms
    public int ladderLength_4_1(String beginWord, String endWord, List<String> wordList) {
        if (beginWord == null || endWord == null || wordList == null) return 0;
        
        int end = wordList.indexOf(endWord);
        if (end == -1) return 0;

        wordList.add(beginWord);
        int start = wordList.size() - 1;

        //用Queue存放word的下标
        Queue<Integer> queue1 = new LinkedList<>();
        Queue<Integer> queue2 = new LinkedList<>();
        //记录是否已被访问过
        Set<Integer> visited1 = new HashSet<>();
        Set<Integer> visited2 = new HashSet<>();
        queue1.offer(start);
        queue2.offer(end);
        visited1.add(start);
        visited2.add(end);
        int cost = 0;

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            cost++;
            //每次遍历一层时，都从层结点少的那端遍历
            //设queue1始终为结点少的Queue
            if (queue1.size() > queue2.size()) {
                Queue<Integer> tmp = queue1;
                queue1 = queue2;
                queue2 = tmp;
                Set<Integer> tmpVisisted = visited1;
                visited1 = visited2;
                visited2 = tmpVisisted;
            }
            int size = queue1.size();
            while (size-- > 0) {
                String currWord = wordList.get(queue1.poll());
                for (int i = 0; i < wordList.size(); i++) {
                    if (visited1.contains(i)) continue;
                    if (!isValid(currWord, wordList.get(i))) continue;
                    if (visited2.contains(i)) return cost + 1;
                    queue1.offer(i);
                    visited1.add(i);
                }
            }
        }
        return 0;
    }
    //双向广度优先搜索
    public int ladderLength_4(String beginWord, String endWord, List<String> wordList) {
        if (beginWord == null || endWord == null || wordList == null) return 0;
        
        int end = wordList.indexOf(endWord);
        if (end == -1) return 0;

        wordList.add(beginWord);
        int start = wordList.size() - 1;

        //用Queue存放word的下标
        Queue<Integer> queue_begin = new LinkedList<>();
        Queue<Integer> queue_end = new LinkedList<>();
        //记录是否已被访问过
        Set<Integer> visited_begin = new HashSet<>();
        Set<Integer> visited_end = new HashSet<>();
        queue_begin.offer(start);
        queue_end.offer(end);
        visited_begin.add(start);
        visited_end.add(end);
        int cost1 = 0, cost2 = 0;
        while (!queue_begin.isEmpty() && !queue_end.isEmpty()) {
            //一端
            cost1++;
            int size1 = queue_begin.size();
            while (size1-- > 0) {
                String currWord = wordList.get(queue_begin.poll());
                //遍历每一个字典中的每一个单词
                for (int i = 0; i < wordList.size(); i++) {
                    if (visited_begin.contains(i)) continue;
                    if (!isValid(currWord, wordList.get(i))) continue;
                    /**
                     * 没有被访问过， 并且是相差一位的单词
                     * 如果另一端也访问过，则找到答案
                     * 否则，加入队列，标记访问
                     */
                    if (visited_end.contains(i)) {
                        return cost1 + cost2 + 1;
                    }
                    queue_begin.offer(i);
                    visited_begin.add(i);
                }
            }
            //另一端
            cost2++;
            int size2 = queue_end.size();
            while(size2-- > 0) {
                String currWord = wordList.get(queue_end.poll());
                for (int i = 0; i < wordList.size(); i++) {
                    if (visited_end.contains(i)) continue;
                    if (!isValid(currWord, wordList.get(i))) continue;
                    if (visited_begin.contains(i)) {
                        return cost1 + cost2 + 1;
                    }
                    queue_end.offer(i);
                    visited_end.add(i);
                }
            }
        }
        return 0;
    }

    //判断是否是相差一位的单词
    private boolean isValid(String s, String t) {
        int diff = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (++diff > 1) return false;
            }
        }
        return diff == 1;
    }
    //单向广度优先搜索 - 80ms
    public int ladderLength_3(String beginWord, String endWord, List<String> wordList) {
        if (beginWord == null || endWord == null || wordList == null) return 0;
        if (!wordList.contains(endWord)) return 0;

        int len = beginWord.length();
        Set<String> words = new HashSet<>(wordList);
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(beginWord);
        int cost = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String currWord = queue.poll();
                /**
                 * 找出所有相邻单词（仅相差一个字符）
                 * 遍历单词中每一个字符，每一个字符再用a-z逐个替换，看是否存在于wordList中
                 * 比对完以后再恢复，继续试探替换下一个字符
                 */
                char[] currWordArr = currWord.toCharArray();
                for (int i = 0; i < len; i++) {
                    char originChr = currWordArr[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (originChr == c) continue;
                        currWordArr[i] = c;
                        String nextWord = String.valueOf(currWordArr);
                        if (words.contains(nextWord)) {
                            if (nextWord.equals(endWord)) return cost + 1;
                            if (!visited.contains(nextWord)) {
                                queue.offer(nextWord);
                                visited.add(nextWord);
                            }
                        }
                    }
                    currWordArr[i] = originChr; //恢复
                }
            }
            cost++;
         }
         return 0;
    }
    //官方题解
    //Soluetion 1. 广度优先搜索 - 54ms
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (beginWord == null || endWord == null || wordList == null) return 0;
        if (!wordList.contains(endWord)) return 0;
        int len = beginWord.length();

        //相当于图的邻接表
        //key is the generic word
        //value is a list of words have the same intermediate generic word
        Map<String, List<String>> dict = new HashMap<>();
        wordList.forEach(word -> {
            for (int i = 0; i < len; i++) {
                String newWord = word.substring(0, i) + '*' + word.substring(i + 1, len);
                List<String> transformations = dict.getOrDefault(newWord, new ArrayList<>());
                transformations.add(word);
                dict.put(newWord, transformations);
            }
        });

        //dict.forEach((a, b) -> System.out.println(a + "-" + b));
        //Pair : word - level
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(beginWord, 1));

        Map<String, Boolean> visited = new HashMap<>();
        visited.put(beginWord, true);

        while (!queue.isEmpty()) {
            Pair node = queue.poll();
            String currWord = node.getKey();
            int level = node.getVal();
            for (int i = 0; i < len; i++) {
                String newWord = currWord.substring(0, i) + '*' + currWord.substring(i + 1, len);
                for (String w : dict.getOrDefault(newWord, new ArrayList<>())) {
                    if (w.equals(endWord)) return level + 1;
                    
                    if(!visited.containsKey(w)) {
                        visited.put(w, true);
                        queue.offer(new Pair(w, level + 1));
                    }
                }
            }
        }
        return 0;
    }

    //Soltuion 2. 双向广度优先搜索 - 30ms
    int len = 0;
    Map<String, List<String>> dict;
    public int ladderLength_2(String beginWord, String endWord, List<String> wordList) {
        if (beginWord == null || endWord == null || wordList == null) return 0;
        if (!wordList.contains(endWord)) return 0;
        len = beginWord.length();

        //相当于图的邻接表
        //key is the generic word
        //value is a list of words have the same intermediate generic word
        dict = new HashMap<>();
        wordList.forEach(word -> {
            for (int i = 0; i < len; i++) {
                String newWord = word.substring(0, i) + '*' + word.substring(i + 1, len);
                List<String> transformations = dict.getOrDefault(newWord, new ArrayList<>());
                transformations.add(word);
                dict.put(newWord, transformations);
            }
        });

        //BFS starting from beginWord
        Queue<Pair> queue_begin = new LinkedList<>();
        queue_begin.offer(new Pair(beginWord, 1));
        //BFS starting from endWord
        Queue<Pair> queue_end = new LinkedList<>();
        queue_end.offer(new Pair(endWord, 1));

        Map<String, Integer> visited_begin = new HashMap<>();
        Map<String, Integer> visited_end = new HashMap<>();
        visited_begin.put(beginWord, 1);
        visited_end.put(endWord, 1);

        dict.forEach((a, b) -> System.out.println(a + "-" + b));
        while (!queue_begin.isEmpty() && !queue_end.isEmpty()) {
            //search from beginWord
            int ans = visitWordNode(queue_begin, visited_begin, visited_end);
            if (ans > -1) return ans;
            //search from endWord
            ans = visitWordNode(queue_end, visited_end, visited_begin);
            if (ans > -1) return ans;
        }

        return 0;
    }

    private int visitWordNode(Queue<Pair> queue, Map<String, Integer> visited, Map<String, Integer> otherVisited) {
        Pair node = queue.poll();
        String currWord = node.getKey();
        int level = node.getVal();

        for (int i = 0; i < len; i++) {
            //Intermediate words for current word
            String newWord = currWord.substring(0, i) + "*" + currWord.substring(i + 1, len);
            for (String w : dict.getOrDefault(newWord, new ArrayList<>())) {
                if (otherVisited.containsKey(w)) {
                    return level + otherVisited.get(w);
                }
                if (!visited.containsKey(w)) {
                    visited.put(w, level + 1);
                    queue.offer(new Pair(w, level + 1));
                }
            }
        }
        return -1;
    }

    public static void main(String args[]) {
        LadderLength ll = new LadderLength();
        String beginWord = "hit";
        String endWord = "cog";
        // String[] words = new String[] {"hot","dot","dog","lot","log","cog"};
        // List<String> wordList = Arrays.asList(words);
        List<String> wordList = new ArrayList<>();
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        wordList.add("cog");
        System.out.println(ll.ladderLength_5(beginWord, endWord, wordList));
    }

}

class Pair {
    String key;
    int val;
    Pair(String key, int val) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public int getVal() {
        return this.val;
    }
    public void setVal(int val) {
        this.val = val;
    }
}
