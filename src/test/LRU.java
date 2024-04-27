package test;
import java.util.HashMap;
public class LRU implements CacheReplacementPolicy
{ //deq
    class Node {
        String word;
        Node prev;
        Node next;
        public Node(String word) {
            this.word = word;
        }
    }
    private  HashMap <String, Integer> nodeMap;
    private  HashMap <String, Node> wordtoNode;
    private Node head;
    private Node tail;
    private Node prev;

    private int key=1;
    public LRU()
    {
        head= new Node(null);
        tail= new Node(null);
        head.prev=tail;
        tail.next=head;
        prev=tail;
        nodeMap= new HashMap<>();
        wordtoNode=new HashMap<>();
    }

    @Override
    public void add(String word)
    {
        if(!nodeMap.containsKey(word)) // if this word isnt alredy in the linked list
        {
            Node addw= new Node(word);
            addw.prev=prev;
            addw.next=head;
            head.prev=addw;
            prev.next=addw;
            prev=addw;
            nodeMap.put(word,key);
            wordtoNode.put(word,addw);
            key+=1;
        }
        else // the word is already in the linked list
        {
            Node cur_node= wordtoNode.get(word);
            moveToHead(cur_node);
        }
    }
    @Override
    public String remove() {
        if (tail.next == head) // means the list is empty
        {
            return null;
        }
        else // the list is not empty
        {
            Node node_rem= tail.next;
            String word_rem= node_rem.word;
            nodeMap.remove(word_rem);
            wordtoNode.remove(node_rem);
            tail.next= node_rem.next;
            (node_rem.next).prev= tail;
            return word_rem;
        }
    }
    public void moveToHead(Node node) {
        if (node == head) {
            // Node is already at the head of the list
            return;
        }

        // Disconnect the node from its current position
        if (node.prev != null)
        {
            node.prev.next = node.next;
        }
        if (node.next != null)
        {
            node.next.prev = node.prev;
        }
        // Update pointers to make the node the new head
        node.next = head;
        node.prev = null;
        if (head != null) {
            head.prev = node;
        }
        head = node;
        // Update the tail if the node was the old tail
        if (node == tail) {
            tail = node.prev;
        }
    }

}
