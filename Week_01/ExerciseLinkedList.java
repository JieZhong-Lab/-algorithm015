package jie.leetcode.camp;

public class ExerciseLinkedList {
	//24. 两两交换链表中的节点
	public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;

        return next;
    }
	public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode prev = dummy;
        ListNode firstNode, secondNode;

        while (head != null && head.next != null) {
            firstNode = head;
            secondNode = head.next;

            //swap
            prev.next = secondNode; //pre -> secondNode
            firstNode.next = secondNode.next;
            secondNode.next = firstNode;

            //reinitall pre node
            prev = firstNode;
            head = firstNode.next;
        }
        return dummy.next;
    }
	
	//21. 合并两个有序链表
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		ListNode ans = new ListNode(0);
		ListNode dummy = ans;

		while (l1 != null && l2 != null) {
			if (l1.val < l2.val) {
				dummy.next = l1;
				l1 = l1.next;
			} else {
				dummy.next = l2;
				l2 = l2.next;
			}
			dummy = dummy.next;
		}

		if (l1 != null)
			dummy.next = l1;
		if (l2 != null)
			dummy.next = l2;
		return ans.next;
	}

	public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
		if (l1 == null)
			return l2;
		if (l2 == null)
			return l1;
		if (l1.val < l2.val) {
			l1.next = mergeTwoLists(l1.next, l2);
			return l1;
		} else {
			l2.next = mergeTwoLists(l1, l2.next);
			return l2;
		}
	}
}

class ListNode {
	int val;
	ListNode next;
	ListNode() {}
	ListNode(int val) {
		this.val = val;
	}
	ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}
}