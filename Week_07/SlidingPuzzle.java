import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SlidingPuzzle {
    //773. 滑动谜题
    //BFS
    public int slidingPuzzle(int[][] board) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        //key - 0's position
        //value - positions that 0 could move
        map.put(0, Arrays.asList(1, 3));
        map.put(1, Arrays.asList(0, 2, 4));
        map.put(2, Arrays.asList(1, 5));
        map.put(3, Arrays.asList(0, 4));
        map.put(4, Arrays.asList(1, 3, 5));
        map.put(5, Arrays.asList(2, 4));

        int steps = 0;
        
        StringBuilder start = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                start.append(board[i][j]);
            }
        }
        String target = "123450";
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(start.toString());
        visited.add(start.toString());

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                String curr_board = queue.poll();
                // System.out.println("board " + curr_board);
                if (curr_board.equals(target)) return steps;
                int idx = curr_board.indexOf('0');
                List<Integer> neighbor = map.get(idx);
                for (int pos : neighbor) {
                    char[] currArr = curr_board.toCharArray();
                    currArr[idx] = currArr[pos];
                    currArr[pos] = '0';
                    String new_board = new String(currArr);
                    
                    if (!visited.contains(new_board)) {
                        queue.add(new_board);
                        visited.add(new_board);
                    }
                }
                size--;
            }
            steps++;
         }
        return -1;
    }

    //A*

    public static void main(String args[]) {
        SlidingPuzzle sPuzzle = new SlidingPuzzle();
        int[][] board = new int[][] {
            {4,1,2},
            {5,0,3}
        };
        System.out.println(sPuzzle.slidingPuzzle(board));
    }
}
