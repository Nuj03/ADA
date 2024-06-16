import java.util.LinkedList;
import java.util.Queue;

//Deci asta ii cea mai mare mizerie si imi bag pula in el copac
//Am dat copy paste si nici nu cred ca il invat
//Mare MUIE

public class BTree {

    private int mindegree; // the mindegree of the B-Tree

    class BTreeNode {
        int nmbKeys;    // current number of keys contained in node
        Integer key[] = new Integer[2 * mindegree - 1];   //maximum 2T-1 keys
        BTreeNode child[] = new BTreeNode[2 * mindegree]; // maximum 2T children
        boolean leaf = true;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(" [ ");
            for (int i = 0; i < nmbKeys; i++)
                sb.append(" " + key[i]);
            sb.append(" ] ");
            return sb.toString();
        }
    }

    public BTree(int t) {
        mindegree = t;
        root = new BTreeNode();
        root.nmbKeys = 0;
        root.leaf = true;
    }

    private BTreeNode root;

    public void insert(Integer key) {
        BTreeNode r = root;
        if (r.nmbKeys == 2 * mindegree - 1) { // if the root node is already full
            BTreeNode s = new BTreeNode(); // preventively splits the root
            root = s;
            s.leaf = false;
            s.nmbKeys = 0;
            s.child[0] = r;
            split(s, 0, r);
            insertNonfullStart(s, key);
        } else {
            insertNonfullStart(r, key);
        }
    }


    /**
     * Splits a node and introduces the new split as a child of the same parent
     * see [CLRS] algorithm
     *
     * @param x   - parent of node to split
     * @param pos - position in parent where to link new node
     * @param y   - node to be split
     */
    private void split(BTreeNode x, int pos, BTreeNode y) {
        System.out.println("Split node " + y.toString());

        BTreeNode z = new BTreeNode();
        z.leaf = y.leaf; // new node z is leaf only if node y to be splitted was leaf
        z.nmbKeys = mindegree - 1;
        for (int j = 0; j < mindegree - 1; j++) { //copy right half of y into new node
            z.key[j] = y.key[j + mindegree];
        }
        if (!y.leaf) {
            for (int j = 0; j < mindegree; j++) {
                z.child[j] = y.child[j + mindegree];
            }
        }

        y.nmbKeys = mindegree - 1;
        for (int j = x.nmbKeys; j >= pos + 1; j--) { //right shift children in parent node
            x.child[j + 1] = x.child[j];
        }
        x.child[pos + 1] = z; //insert new node z as child of parent

        for (int j = x.nmbKeys - 1; j >= pos; j--) {
            x.key[j + 1] = x.key[j];
        }
        x.key[pos] = y.key[mindegree - 1];
        x.nmbKeys = x.nmbKeys + 1;
    }


    /**
     * Inserts key k into node x which is assumed to be non-full when function is called.
     * This function recurses as necessary down the tree, at all times guaranteeing that
     * the node to which it recurses is not full by calling split as necessary
     * see [CLRS] algorithm
     *
     * @param x - root (non-full node) of subtree where insertion is done
     * @param k - new key to be inserted
     */
    final private void insertNonfullStart(BTreeNode x, int k) {

        if (x.leaf) {
            // x is a non-full leaf node, insert key into it
            int i = 0;
            // shift existing keys right to make place for new k
            for (i = x.nmbKeys - 1; i >= 0 && k < x.key[i]; i--) {
                x.key[i + 1] = x.key[i];
            }
            x.key[i + 1] = k;
            x.nmbKeys = x.nmbKeys + 1;
        } else { // x is not a leaf
            int i = 0;
            for (i = x.nmbKeys - 1; i >= 0 && k < x.key[i]; i--) {
            } // search child to continue insertion
            ;
            i++;
            BTreeNode tmp = x.child[i];
            if (tmp.nmbKeys == 2 * mindegree - 1) {
                // if child is full, split it
                split(x, i, tmp);
                if (k > x.key[i]) { // determines in which split half we insert
                    i++;
                }
            }
            insertNonfullStart(x.child[i], k); // recursive insert
        }

    }

    private class QueuePair {
        BTreeNode node;
        int level;

        QueuePair(BTreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    public void displayLevels() {
        // Use Queue to hold nodes while printing on levels
        Queue<QueuePair> q = new LinkedList<QueuePair>();

        System.out.println("B Tree displayed on levels: ");
        BTreeNode x = root;
        int oldLevel = 0;
        int level;

        q.add(new QueuePair(x, oldLevel));

        while (!q.isEmpty()) {

            QueuePair p = q.remove();
            x = p.node;
            level = p.level;

            if (level > oldLevel) {
                System.out.println(); // level changed
                oldLevel = level;
            }
            System.out.print(x.toString());
            if (!x.leaf) {
                for (int i = 0; i <= x.nmbKeys; i++) {
                    BTreeNode y = (x.child)[i];
                    q.add(new QueuePair(y, level + 1));
                }
            }
        }
        System.out.println();
    }
}