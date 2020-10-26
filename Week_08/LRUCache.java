import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private Map<Integer, DLinkNode> cache = new HashMap<>();
    private int size;
    private int capacity;
    private DLinkNode head, tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        //虚拟头结点与尾结点
        head = new DLinkNode();
        tail = new DLinkNode();
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        DLinkNode node = cache.get(key);
        if (node == null) return -1;
        moveToHead(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        DLinkNode node = cache.get(key);
        if (node == null) {
            DLinkNode newNode = new DLinkNode(key, value);
            cache.put(key, newNode);
            addToHead(newNode);
            size++;
            if (size > capacity) {
                //超出容量，删除尾结点
                DLinkNode tail = removeTail();
                cache.remove(tail.key);
                size--;
            }
        } else {
            //如果存在，更新value, 并移到头部
            node.val = value;
            moveToHead(node);
        }
    }

    private void moveToHead(DLinkNode node) {
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(DLinkNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private DLinkNode removeTail() {
        DLinkNode lastNode = tail.prev;
        removeNode(lastNode);
        return lastNode;
    }
}

class DLinkNode {
    int key;
    int val;
    DLinkNode prev;
    DLinkNode next;
    public DLinkNode() {};
    public DLinkNode(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
