import java.util.LinkedList;
import java.util.Queue;

import static java.util.Collections.min;

class AVLNode{
    Integer key;
    Integer value;
    AVLNode left, right;
    Integer height;
    //ii efectiv acelasi nod la ca bst numa cu figuri
    public AVLNode(Integer key, Integer value, Integer height) {
        this.key = key;
        this.value = value;
        this.height = height;
        this.left = this.right = null;
    }
}
public class AVL {
    private AVLNode root;
    public AVL() {
        root = null;
    }

    private int height(AVLNode node) {
        if(node == null) return -1;
        return node.height;
    }
    //nu ma intreba cum mere cu rotatiile astea ca doar Dumnezeu stie si si el o uitat
    private AVLNode balance(AVLNode node) {
        if(balanceFactor(node) < -1){
            if (balanceFactor(node.right) > 0)
                node.right = rotateRight(node.right);
            node = rotateLeft(node);
        }
        else if(balanceFactor(node) > 1) {
            if (balanceFactor(node.left) < 0)
                node.left = rotateLeft(node.left);
            node = rotateRight(node);
        }
        return node;
    }
    private int balanceFactor(AVLNode node){
        return height(node.left) - height(node.right);
    }

    private AVLNode rotateLeft(AVLNode node){
        AVLNode temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return temp;
    }
    private AVLNode rotateRight(AVLNode node){
        AVLNode temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return temp;
    }

    public void insert(Integer key, Integer value) {
        if (key == null) throw new IllegalArgumentException("The key is null");
        root = insert(root, key, value);
    }
    private AVLNode insert(AVLNode node, Integer key, Integer value) {
        if (node == null) return new AVLNode(key,value,0);
        if (key < node.key) node.left = insert(node.left, key, value);
        else if (key > node.key) node.right = insert(node.right, key, value);
        else {
            node.value = value;
            return node;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    //nmbKeys-am mai facut delete, ca mi lene ca trebe sa mai bag si minim si plm nmbKeys-am chef
    //ii la fel ca la bst da mai trebe sa balansezi cred



    //astea 2 nuj ce is sa mor eu, ceva cu nivele si mortii ma-sii, astea doar le-am copiat
    private class QueuePair {
        AVLNode node;
        int level;
        QueuePair(AVLNode node, int level){
            this.node=node;
            this.level=level;
        }
    }

    public void displayLevels() {
        // Use Queue to hold nodes while printing on levels
        Queue<QueuePair> q = new LinkedList<QueuePair>();

        System.out.print("AVL Tree displayed on levels: ");
        AVLNode x = root;
        int oldLevel = -1;
        int level = 0;

        if (root != null) q.add(new QueuePair(x, level));

        while (!q.isEmpty()) {

            QueuePair p = q.remove();
            x= p.node;
            level = p.level;

            if (level > oldLevel) {
                System.out.println();
                System.out.print("Level " + level + ":  "); // level changed
                oldLevel = level;
            }

            System.out.print(x.key + " ");
            if (x.left != null) q.add(new QueuePair(x.left, level+1));
            if (x.right != null) q.add(new QueuePair(x.right, level+1));
        }
        System.out.println();
        System.out.println();
    }
}
