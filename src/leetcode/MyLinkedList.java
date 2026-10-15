package leetcode;

public class MyLinkedList {
    ListNode head;
    
    // 头插
    public void addFirst(int val) {
        ListNode node = new ListNode(val);
        node.next = head;
        head = node;
    }
    
    // 尾插
    public void addLast(int val) {
        ListNode node = new ListNode(val);
        if (head == null) {
            head = node;
            return;
        }
        ListNode cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = node;
    }
    
    // 删除第一个值为 val 的节点
    public void delete(int val) {
        if (head == null) return;
        
        if (head.val == val) {
            head = head.next;
            return;
        }
        
        ListNode prev = head;
        ListNode cur = head.next;
        while (cur != null) {
            if (cur.val == val) {
                prev.next = cur.next;
                return;
            }
            prev = cur;
            cur = cur.next;
        }
    }
    
    // 查找
    public boolean contains(int val) {
        ListNode cur = head;
        while (cur != null) {
            if (cur.val == val) return true;
            cur = cur.next;
        }
        return false;
    }
    
    // 遍历打印
    public void print() {
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val + " -> ");
            cur = cur.next;
        }
        System.out.println("null");
    }
}

