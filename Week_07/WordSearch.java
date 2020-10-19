import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordSearch {
    //79. 单词搜索
    public boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) return false;
        if (board == null || board.length == 0 || board[0].length == 0) return false;

        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                boolean result = dfs(board, visited, r, c, word, 0);
                if (result) return true;
            }
        }
        return false;
    }

    //check if the word [ind, word.length - 1] is exist in board
    private boolean dfs(char[][] board, boolean[][] visited, int r, int c, String word, int ind) {
        //terminator
        if (board[r][c] != word.charAt(ind)) return false;
        if (ind == word.length() - 1) return true;
        
        int[] dx = new int[]{0, 0, 1, -1};
        int[] dy = new int[]{1, -1, 0, 0};

        boolean result = false;

        visited[r][c] = true;
        for (int i = 0; i < 4; i++) {
            int rr = r + dx[i];
            int cc = c + dy[i];

            if (rr >= 0 && rr < board.length && cc >= 0 && cc < board[0].length) {
                if (!visited[rr][cc]) {
                    //drill down
                    boolean existFlag = dfs(board, visited, rr, cc, word, ind + 1);
                    if (existFlag) {
                        result = true;
                        break;
                    }
                }
            }
        }
        //restore current state
        visited[r][c] = false;
        return result;
    }

    //212. 单词搜索 II
    int m, n;
    int[] dx = new int[]{-1, 1, 0, 0};
    int[] dy = new int[]{0, 0, -1, 1};
    public List<String> findWords(char[][] board, String[] words) {
        if (board == null || board.length == 0 || board[0].length == 0) return new ArrayList<>();
        //构建字典树
        MyTrie wordTrie = new MyTrie();
        for (String word : words) {
            wordTrie.insert(word);
        }
        NodeOfTrie root = wordTrie.getRoot();

        Set<String> result =  new HashSet<>();
        m = board.length; n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        //遍历整个board
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, i, j, visited, result, root);
            }
        }
        return new ArrayList<String>(result);
    }

    private void dfs(char[][] board, int r, int c, boolean[][] visited, Set<String> result, NodeOfTrie node) {
        //terminator
        if (r < 0 || r >= m || c < 0 || c >= n || visited[r][c]) return;
        NodeOfTrie curr = node.getSubNode(board[r][c]);
        if (curr == null) return;
        if (curr.isEnd()) { //找到单词
            result.add(curr.getWordVal());
        }
        //process current logic
        visited[r][c] = true;
        //drill down
        for (int i = 0; i < 4; i++) {
            int rr = r + dx[i], cc = c + dy[i];
            dfs(board, rr, cc, visited, result, curr);
        }
        //restor current state
        visited[r][c] = false; 
    }
    
    public static void main(String[] args) {
        WordSearch ws = new WordSearch();
        char[][] board = new char[][]{{'o','a','a','n'},
                                      {'e','t','a','e'},
                                      {'i','h','k','r'},
                                      {'i','f','l','v'}};
        // ws.exist(board, "ABCCED");
        String[] words = new String[]{"oath", "pea", "eat", "rain"};
        List<String> list = ws.findWords(board, words);
        list.forEach(word -> System.out.println(word));
    }
}

class MyTrie {
    private NodeOfTrie root = new NodeOfTrie();
    public void insert(String s) {
        NodeOfTrie node  = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (node.getSubNode(c) == null) {
                node.setSubNode(c);
            } 
            node = node.getSubNode(c);
        } 
        node.setEnd();
        node.setWordVal(s);
    }

    public NodeOfTrie getRoot() {
        return root;
    }
}

class NodeOfTrie {
    private NodeOfTrie[] children;
    private boolean isEnd = false;
    private String wordVal;

    public NodeOfTrie() {
        children = new NodeOfTrie[26];
    }

    public NodeOfTrie getSubNode(char ch) {
        return children[ch - 'a'];
    }

    public void setSubNode(char ch) {
        children[ch - 'a'] = new NodeOfTrie();
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd() {
        isEnd = true;
    }

    public String getWordVal() {
        return wordVal;
    }

    public void setWordVal(String wordVal) {
        this.wordVal = wordVal;
    }
}
