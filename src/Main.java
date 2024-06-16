
public class Main {
    public static void testBST(){
        int[] a = {20, 30, 15, 1, 7, 9, 29, 11, 12, 4};

        BST bst = new BST();

        for (int i = 0; i < a.length; i++) {
            bst.insert(a[i], a[i]);
        }

        bst.preorder();
        System.out.println();
        bst.inorder();
        System.out.println();
        bst.postorder();

    }
    public static void testAVL() {
        AVL avl = new AVL();

        int[] a = {8, 9, 10, 11, 15, 3, 17, 22, 25, 16};

        for (int i = 0; i < a.length; i++) {
            System.out.println("Insert " + a[i]);
            avl.insert(a[i], i);
            avl.displayLevels();
        }
    }
    public static void testBTree() {
        BTree b = new BTree(3);

        int[] a={8,9,10,11,15,20, 17, 22, 25,16, 12, 13, 14, 26, 27, 29, 33, 40, 50, 51, 52, 53};

        for (int i=0; i<a.length; i++) {
            System.out.println("Will insert "+a[i]);
            b.insert(a[i]);
            b.displayLevels();
        }
    }

    public static void main(String[] args) {
        //testBST();
        //System.out.println();
        //testAVL();
        testBTree();
    }
}