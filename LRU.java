// T.C -> O(1) get, put
// S.C -> O(N) 
// Solved on Leetcode: Yes

class LRUCache {

    class Node {
        int key;
        int val;
        Node prev;
        Node next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    Node head;
    Node tail;
    HashMap<Integer, Node> map;
    int capacity;

    public LRUCache(int capacity) {
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        this.head.next = this.tail;
        this.tail.prev = this.head;
        this.capacity = capacity;
        map = new HashMap<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        removeNode(node);
        addToHead(node);
        return node.val;
    }

    private void removeNode(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next = node;
        node.next.prev = node;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            removeNode(node);
            map.remove(key);
            addToHead(node);
            map.put(key, node);
        } else {
            Node newNode = new Node(key, value);
            if (map.size() == capacity) {
                Node beforeTail = tail.prev;
                removeNode(beforeTail);
                map.remove(beforeTail.key);
                addToHead(newNode);
                map.put(key, newNode);
            } else {
                addToHead(newNode);
            }
            map.put(newNode.key, newNode);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */