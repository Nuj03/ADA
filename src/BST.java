
import static java.lang.Math.min;

class Node{
    Integer key;
    Integer value;
    Node left, right;

    public Node(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
public class BST {
    private Node root;

    public BST() {
        root = null;
    }

    public void insert(Integer key, Integer value) {
        if (key == null) throw new IllegalArgumentException("can't insert null key");
        root = insert(root, key, value);
    }
    private Node insert(Node node, Integer key, Integer val) {
        if (node == null) return new Node(key, val);
        if (key.compareTo(node.key) < 0) node.left = insert(node.left, key, val);
        else if (key.compareTo(node.key) > 0) node.right = insert(node.right, key, val);
        else node.value = val;
        return node;
    }

    public void delete(Integer key) {
        if (key == null) throw new IllegalArgumentException("can't delete null key");
        root = deleteRecursive(root, key);
    }
    private Node deleteRecursive(Node toDelete, Integer key) {
        if (toDelete == null) return null;
        if (key.compareTo(toDelete.key) < 0) toDelete.left = deleteRecursive(toDelete.left, key);
        else if (key.compareTo(toDelete.key) > 0) toDelete.right = deleteRecursive(toDelete.right, key);
        else {
            if (toDelete.left == null) return toDelete.right;
            else if (toDelete.right == null) return toDelete.left;
            Node successor = min(toDelete.right);
            toDelete.right = deleteRecursive(toDelete.right, successor.key);
            toDelete.key = successor.key;
        }
        return toDelete;
    }

    public Integer min() {
        if (root == null) throw new IllegalArgumentException("calls min() with empty BST");
        return min(root).key;
    }
    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    public Integer max() {
        if (root == null) throw new IllegalArgumentException();
        return max(root).key;
    }
    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    public void inorder() {
        inorder(root);
    }
    private void inorder(Node x) {
        if (x == null) return;
        inorder(x.left);
        System.out.print(x.key + " ");
        inorder(x.right);
    }

    public void preorder() {
        preorder(root);
    }
    private void preorder(Node x) {
        if (x == null) return;
        System.out.print(x.key + " ");
        preorder(x.left);
        preorder(x.right);
    }

    public void postorder() {
        postorder(root);
    }
    private void postorder(Node x) {
        if (x == null) return;
        postorder(x.left);
        postorder(x.right);
        System.out.print(x.key + " ");
    }

    public boolean contains(Integer key) {
        return contains(root, key);
    }
    private boolean contains(Node x, Integer key) {
        if (x == null) return false;
        if (key.compareTo(x.key) < 0) return contains(x.left, key);
        else if (key.compareTo(x.key) > 0) return contains(x.right, key);
        else return true;
    }
}
