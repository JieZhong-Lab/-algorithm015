import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class W7Review {
    // 24. 两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }

    public ListNode swapPairs_2(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode prev = dummy;
        ListNode first, second;

        while (prev.next != null && prev.next.next != null) {
            first = prev.next;
            second = prev.next.next;

            // 设定prev的指向，swap
            prev.next = second;
            first.next = second.next;
            second.next = first;

            // reinitial prev
            prev = first;
        }

        return dummy.next;
    }

    // 347. 前 K 个高频元素
    public int[] topKFrequent(int[] nums, int k) {
        int[] ans = new int[k];
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        // 大顶堆
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>(
                (o1, o2) -> (o2.getValue() - o1.getValue()));
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            maxHeap.offer(entry);
        }
        for (int i = 0; i < k; i++) {
            ans[i] = maxHeap.poll().getKey();
        }
        return ans;
    }

    public int[] topKFrequent_2(int[] nums, int k) {
        int[] ans = new int[k];
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        // 小顶堆
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a1, a2) -> map.get(a1) - map.get(a2));

        for (Integer key : map.keySet()) {
            if (minHeap.size() < k) {
                minHeap.offer(key);
            } else {
                if (map.get(key) > map.get(minHeap.peek())) {
                    minHeap.poll();
                    minHeap.offer(key);
                }
            }
        }
        int i = 0;
        while (!minHeap.isEmpty()) {
            ans[i++] = minHeap.poll();
        }
        return ans;
    }

    // 844. 比较含退格的字符串
    public boolean backspaceCompare(String S, String T) {
        if (S == null || T == null)
            return S == T;
        return build(S).equals(build(T));

    }

    private String build(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (str.charAt(i) != '#') {
                sb.append(c);
            } else {
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }
        return sb.toString();
    }

    public boolean backspaceCompare_2(String S, String T) {
        if (S == null || T == null)
            return S == T;
        int i = S.length(), j = T.length();
        int skip1 = 0, skip2 = 0;
        // 从后往前遍历，skip表示当前待删除的字符

        while (i >= 0 && j >= 0) {
            while (i >= 0) {
                if (S.charAt(i) == '#') {
                    skip1++;
                    i--;
                } else if (skip1 > 0) {
                    skip1--;
                    i--; // 当前字符要删去
                } else {
                    break;
                }
            }
            while (j >= 0) {
                if (T.charAt(j) == '#') {
                    skip2++;
                    j--;
                } else if (skip2 > 0) {
                    skip2--;
                    j--;
                } else {
                    break;
                }
            }
            if (i >= 0 && j >= 0) {
                if (S.charAt(i) != T.charAt(j))
                    return false;
            } else {
                if (i >= 0 || j >= 0)
                    return false;
            }
            i--;
            j--;
        }
        return true;
    }

    // 143. 重排链表
    public void reorderList(ListNode head) {
        if (head == null || head.next == null)
            return;
        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }

        int i = 0, j = list.size() - 1;
        while (i < j) {
            list.get(i).next = list.get(j);
            i++;
            if (i == j)
                break;
            list.get(j).next = list.get(i);
            j--;
        }
        list.get(i).next = null;
    }

    //快慢指针
    public void reorderList_2(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return;

        // 1. Find middle Node;
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        } // 当结点数为偶数时，slow停留在靠左的中间结点
        ListNode l1 = head;
        ListNode l2 = slow.next;
        slow.next = null; // 使 l1 只有原链表的前半部分
        // 2. Reverse the linked list l2
        l2 = reverse(l2);
        // 3. Merget l1 and l2
        merge(l1, l2);
    }

    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode tmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = tmp;
        }
        return prev;
    }

    private void merge(ListNode l1, ListNode l2) {
        ListNode tmp1, tmp2;
        while (l1 != null && l2 != null) {
            tmp1 = l1.next;
            tmp2 = l2.next;

            l1.next = l2;
            l1 = tmp1;

            l2.next = l1;
            l2 = tmp2;
        }
    }

    // 925. 长按键入
    // 1. 双指针
    public boolean isLongPressedName(String name, String typed) {
        if (name.equals(typed))
            return true;
        if (name.length() >= typed.length())
            return false;
        int i = 0, j = 0;
        while (j < typed.length()) {
            if (i < name.length() && name.charAt(i) == typed.charAt(j)) {
                i++;
                j++;
            } else if (j > 0 && typed.charAt(j - 1) == typed.charAt(j)) {
                j++;
            } else {
                return false;
            }
        }
        return i == name.length();
    }

    public boolean isLongPressedName_2(String name, String typed) {
        if (name.equals(typed)) return true;
        if (name.length() >= typed.length()) return false;
        Stack<Character> stack = new Stack<>();
        for (char c : name.toCharArray()) stack.push(c);

        for (int i = typed.length(); i >= 0; i--) {
            if (!stack.isEmpty()) {
                if (stack.peek() == typed.charAt(i)) stack.pop();
                else if (i + 1 < typed.length()) {
                    if (typed.charAt(i + 1) == typed.charAt(i)) 
                        continue;
                    else 
                        return false;
                } else {
                    return false;
                }
            } else {
                if (typed.charAt(i) == typed.charAt(i + 1)) continue;
                else return false;
            }
        }
        return stack.isEmpty();
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode (int x) {
        val = x;
    }
}