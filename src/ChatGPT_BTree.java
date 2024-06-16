import java.util.LinkedList;
import java.util.Queue;

public class ChatGPT_BTree {
    private int T; // Minimum degree of B-Tree
    private Node root; // Root node

    // Node class represents a single node in the B-Tree
    class Node {
        int n; // Current number of keys
        int[] keys; // Array of keys
        Node[] children; // Array of child pointers
        boolean isLeaf; // True if leaf node

        Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = new int[2 * T - 1];
            this.children = new Node[2 * T];
            this.n = 0;
        }
    }

    // Constructor to create a new B-Tree with minimum degree T
    public ChatGPT_BTree(int t) {
        this.T = t;
        this.root = new Node(true);
    }

    // Method to insert a new key into the B-Tree
    public void insert(int key) {
        Node r = root;
        if (r.n == 2 * T - 1) { // If root is full, split it
            Node s = new Node(false);
            root = s;
            s.children[0] = r;
            split(s, 0, r);
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    // Method to split a full child node
    private void split(Node parent, int i, Node fullNode) {
        Node newNode = new Node(fullNode.isLeaf);
        newNode.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            newNode.keys[j] = fullNode.keys[j + T];
        }
        if (!fullNode.isLeaf) {
            for (int j = 0; j < T; j++) {
                newNode.children[j] = fullNode.children[j + T];
            }
        }
        fullNode.n = T - 1;
        for (int j = parent.n; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[i + 1] = newNode;
        for (int j = parent.n - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }
        parent.keys[i] = fullNode.keys[T - 1];
        parent.n++;
    }

    // Method to insert a key into a non-full node
    private void insertNonFull(Node node, int key) {
        int i = node.n - 1;
        if (node.isLeaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.n++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;
            if (node.children[i].n == 2 * T - 1) {
                split(node, i, node.children[i]);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

    // Method to display the B-Tree level by level
    public void displayLevels() {
        if (root == null) return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            while (levelSize-- > 0) {
                Node node = queue.poll();
                System.out.print("[ ");
                for (int i = 0; i < node.n; i++) {
                    System.out.print(node.keys[i] + " ");
                }
                System.out.print("] ");
                if (!node.isLeaf) {
                    for (int i = 0; i <= node.n; i++) {
                        queue.add(node.children[i]);
                    }
                }
            }
            System.out.println();
        }
    }
}
