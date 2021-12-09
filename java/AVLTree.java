package main.java;

/**
 * AVLTree
 * <p>
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 */

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * AVLTree
 * <p>
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 */

public class AVLTree {

    private AVLNode root = new AVLNode();
    private int numberOfRotations = 0; // last occurnece of number of rotation
    private int size = 0; // how many items there are in the tree
    private int inorderOrderCount = 0;
    public double averageJoin = 0.0;
    public double maxJoin = 0;

    // Time complexity: O(1)

    /**
     * public boolean empty()
     * <p>
     * Returns true if and only if the tree is empty.
     */
    public boolean empty() {
        return !this.root.isRealNode();
    }

    public AVLTree() {
        this.size = 0;
        this.root = new AVLNode();
    }

    public AVLTree(AVLNode avlNode) {
        this.root = avlNode;
        this.root.parent = new AVLNode();
        if (this.root.isRealNode()) {
            this.size = avlNode.size;
        } else {
            this.size = 0;
        }
    }

    // Time complexity: O(log(n))

    /**
     * public String search(int k)
     * <p>
     * Returns the info of an item with key k if it exists in the tree.
     * otherwise, returns null.
     */
    public String search(int k) {
        AVLNode node = this.root;
        return searchRec(node, k);
    }

    // Time complexity: O(log(n))
    private String searchRec(AVLNode root, int key) {
        if (!root.isRealNode()) return null;

        if (root.key == key) return root.value;

        // then recur on left subtree /
        String res1 = searchRec(root.leftSon, key);

        // node found, no need to look further
        if (res1 != null) return res1;

        // node is not found in left,
        // so recur on right subtree /

        return searchRec(root.rightSon, key);
    }

    // Time complexity: O(log(n))
    public AVLNode getItemWithKey(int key) {
        AVLNode node = this.root;
        return getItemsWithKeyRec(node, key);
    }

    // Time complexity: O(log(n))
    private AVLNode getItemsWithKeyRec(AVLNode root, int key) {
        if (!root.isRealNode()) return null;

        if (root.key == key) return root;

        // then recur on left subtree /
        AVLNode res1 = getItemsWithKeyRec(root.leftSon, key);

        // node found, no need to look further
        if (res1 != null) return res1;

        // node is not found in left,
        // so recur on right subtree /

        return getItemsWithKeyRec(root.rightSon, key);
    }

    // Time complexity: O(log(n))

    /**
     * public int insert(int k, String i)
     * <p>
     * Inserts an item with key k and info i to the AVL tree.
     * The tree must remain valid, i.e. keep its invariants.
     * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
     * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
     * Returns -1 if an item with key k already exists in the tree.
     */
    public int insert(int k, String i) {
        if (search(k) != null) return -1;
        this.root = insertRec(root, k, i);
        this.root.parent = new AVLNode();
        this.size = this.size + 1;
        int temp = this.numberOfRotations;
        this.numberOfRotations = 0;
        return temp;
    }

    // Time complexity: O(log(n))
    /* A recursive function to
       insert a new key in BST */
    AVLNode insertRec(AVLNode root, int key, String info) {
        /* If the tree is empty,
           return a new node */
        if (!root.isRealNode()) {
            root = new AVLNode(key, info);
            return root;
        }

        /* Otherwise, recur down the tree */
        if (key < root.key) {
            root.setLeft(insertRec(root.leftSon, key, info));
        } else if (key > root.key) {
            root.setRight(insertRec(root.rightSon, key, info));
        }
        root.setHeight(1 + Math.max((root.leftSon.getHeight()),
                (root.rightSon.getHeight())));
        root.size += 1;

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = balanceFactor(root);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && key < root.leftSon.key) {
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && key > root.rightSon.key) {
            return leftRotate(root);
        }

        // Left Right Case
        if (balance > 1 && key > root.leftSon.key) {
            root.setLeft(leftRotate(root.leftSon));
            return rightRotate(root);
        }

        // Right Left Case
        if (balance < -1 && key < root.rightSon.key) {
            root.setRight(rightRotate(root.rightSon));
            return leftRotate(root);
        }

        /* return the (unchanged) node pointer */
        return root;
    }

    public int distanceFromMax() {
        int count = 0;
        AVLNode curr = this.root;
        while (curr.isRealNode()) {
            curr = curr.rightSon;
            count += 1;
        }
        return count;
    }

    public int distanceFromMin() {
        int count = 0;
        AVLNode curr = this.root;
        while (curr.isRealNode()) {
            curr = curr.leftSon;
            count += 1;
        }
        return count;
    }
    // Time complexity: O(log(n))

    /**
     * public int delete(int k)
     * <p>
     * Deletes an item with key k from the binary tree, if it is there.
     * The tree must remain valid, i.e. keep its invariants.
     * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
     * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
     * Returns -1 if an item with key k was not found in the tree.
     */
    public int delete(int k) {
        if (search(k) == null) return -1;
        // Delete nodes returns the root of the tree after the operations.
        AVLNode aboveX = getItemWithKey(k);

        this.root = deleteNode(this.root, k);
        this.root.setParent(new AVLNode());

        while (aboveX.parent.isRealNode()) {
            aboveX = aboveX.parent;
            aboveX.height = 1 + Math.max(aboveX.leftSon.height, aboveX.rightSon.height);
            balanceTree(aboveX);
        }

        this.size = this.size - 1;
        int temp = this.numberOfRotations;
        this.numberOfRotations = 0;
        return temp;
    }

    // Time complexity: O(log(n))
    // The node must exist because we found it in delete.
    AVLNode deleteNode(AVLNode root, int k) {
        // Base case
        if (!root.isRealNode())
            return root;

        // Recursive calls for ancestors of
        // node to be deleted
        if (root.key > k) {
            root.size -= 1;
            root.setLeft(deleteNode(root.leftSon, k));
            return root;
        } else if (root.key < k) {
            root.size -= 1;
            root.setRight(deleteNode(root.rightSon, k));
            return root;
        }

        // We reach here when root is the node
        // to be deleted.

        // If one of the children is empty
        if (!root.leftSon.isRealNode()) {
            return root.rightSon;
        } else if (!root.rightSon.isRealNode()) {
            return root.leftSon;
        }

        // If both children exist
        else {
            AVLNode succParent = root;

            // Find successor
            AVLNode succ = root.rightSon;

            while (!succ.leftSon.isRealNode()) {
                succParent = succ;
                succ = succ.leftSon;
            }

            // Delete successor. Since successor
            // is always left child of its parent
            // we can safely make successor's right
            // right child as left of its parent.
            // If there is no succ, then assign
            // succ->right to succParent->right
            if (!succParent.key.equals(root.key))
                succParent.setLeft(succ.rightSon);
            else
                succParent.setRight(succ.rightSon);

            // Copy Successor Data to root
            // no need the swapped node size as we already updated the size before swapping with successor
            root.key = succ.key;
            root.value = succ.value;

            return root;
        }
    }

    // Time complexity: O(log(n))
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        if (!current.isRealNode()) return new AVLNode();
        /* loop down to find the leftmost leaf */
        while (current.leftSon.isRealNode())
            current = current.leftSon;

        return current;
    }

    // Time complexity: O(log(n))
    public AVLNode maxValueNode(AVLNode node) {
        AVLNode current = node;
        if (!current.isRealNode()) return new AVLNode();
        /* loop down to find the rightmost leaf */
        while (current.rightSon.isRealNode())
            current = current.rightSon;

        return current;
    }

    // Time complexity: O(log(n))

    /**
     * public String min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty.
     */
    public String min() {
        AVLNode node = minValueNode(this.root);
        if (node == null || !node.isRealNode()) return null;
        return node.value;
    }

    // Time complexity: O(log(n))

    /**
     * public String max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty.
     */
    public String max() {
        AVLNode node = maxValueNode(this.root);
        if (node == null) return null;
        return node.value;
    }

    // Time complexity: O(n)
    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */
    public int[] keysToArray() {
        return inOrderKeys(this.root, new int[size]);
    }

    // Time complexity: O(n)
    private int[] inOrderKeys(AVLNode root, int[] arr) {
        if (!root.isRealNode())
            return arr;

        /* first recur on left child */
        inOrderKeys(root.leftSon, arr);

        /* then print the data of node */
        arr[inorderOrderCount] = root.key;
        inorderOrderCount += 1;
        if (inorderOrderCount == arr.length) {
            this.inorderOrderCount = 0;
        }

        /* now recur on right child */
        inOrderKeys(root.rightSon, arr);
        return arr;
    }

    // Time complexity: O(n)
    // A method that is used for testing, doesn't affect the complexity of the AVLTree.
    public int calculatedSize(AVLNode root) {
        if (!root.isRealNode())
            return 0;
        return 1 + calculatedSize(root.leftSon) + calculatedSize(root.rightSon);
    }

    // Time complexity: O(n))
    private String[] inOrderInfo(AVLNode root, String[] arr) {
        if (!root.isRealNode())
            return arr;

        /* first recur on left child */
        inOrderInfo(root.leftSon, arr);

        /* then print the data of node */
        arr[inorderOrderCount] = root.value;
        inorderOrderCount += 1;
        if (inorderOrderCount == arr.length) {
            this.inorderOrderCount = 0;
        }

        /* now recur on right child */
        inOrderInfo(root.rightSon, arr);
        return arr;
    }

    // Time complexity: O(n))

    /**
     * public String[] infoToArray()
     * <p>
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */
    public String[] infoToArray() {
        return inOrderInfo(this.root, new String[this.size]);
    }

    // Time complexity: O(1))

    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     */
    public int size() {
        return this.size;
    }

    // Time complexity: O(1))

    /**
     * public int getRoot()
     * <p>
     * Returns the root AVL node, or null if the tree is empty
     */
    public IAVLNode getRoot() {
        return this.root;
    }

    // Time complexity: O(log(n)))

    /**
     * public AVLTree[] split(int x)
     * <p>
     * splits the tree into 2 trees according to the key x.
     * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
     * <p>
     * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
     * postcondition: none
     */
    public AVLTree[] split(int x) {
        double totalJoin = 0;
        int numOfJoin = 0;
        AVLNode xNode = getItemWithKey(x);
        AVLTree t1 = new AVLTree(xNode.leftSon);
        AVLTree t2 = new AVLTree(xNode.rightSon);
        while (xNode.parent.isRealNode()) {
            // The xNode parent turned right to him
            if (xNode.parent.rightSon == xNode) {
                int joinAmount = t1.join(new AVLNode(xNode.parent), new AVLTree(xNode.parent.leftSon));
                maxJoin = Math.max(maxJoin, joinAmount);
                totalJoin += joinAmount;
                numOfJoin += 1;
            }
            // The xNode parent turned left to him
            else {
                int joinAmount = t2.join(new AVLNode(xNode.parent), new AVLTree(xNode.parent.rightSon));
                totalJoin += joinAmount;
                maxJoin = Math.max(maxJoin, joinAmount);
                numOfJoin += 1;
            }
            xNode = xNode.parent;
        }
        AVLTree[] trees = new AVLTree[2]; // combining both statements in one
        trees[0] = t1;
        trees[1] = t2;
        if (numOfJoin > 0) {
            this.averageJoin = (totalJoin / numOfJoin);
        }
        return trees;
    }

    // Time complexity: O(log(n)))

    /**
     * public int join(IAVLNode x, AVLTree t)
     * <p>
     * joins t and x with the tree.
     * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
     * <p>
     * precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
     * postcondition: none
     */
    public int join(IAVLNode x, AVLTree t) {
        int cost = Math.abs(this.root.getHeight() - t.getRoot().getHeight()) + 1;
        joinRec(x, this, t);
        this.root.parent = new AVLNode();
        this.size = this.size + t.size + 1;
        this.root.size = this.size;
        return cost;
    }

    // Time complexity: O(log(n)))
    private void joinRec(IAVLNode x, AVLTree t1, AVLTree t2) {
        int t1Height = t1.getRoot().getHeight();
        int t2Height = t2.getRoot().getHeight();

        if (t1Height == t2Height) {
            AVLNode temp = (AVLNode) t1.getRoot();
            x.setRight(temp.key >= t2.getRoot().getKey() ? temp : t2.getRoot());
            x.setLeft(temp.key < t2.getRoot().getKey() ? temp : t2.getRoot());
            x.setHeight(1 + Math.max(x.getLeft().getHeight(), x.getRight().getHeight()));
            if (x.getHeight() > root.height) {
                this.root = (AVLNode) x;
            }
            return;
        }

        // determine height of both trees
        AVLTree bigTree = t1Height > t2Height ? t1 : t2;
        AVLTree smallTree = t1Height > t2Height ? t2 : t1;

        AVLNode currNode = bigTree.root;

        // big tree = tree with higher height, small tree, tree with smaller height.
        // bigTree.root.key > smallTree.root.key -> bigTree is on theRight -> go left
        // bigTree.root.key < smallTree.key -> bigTree is on the left -> go right
        boolean goRight = !(bigTree.root.key > smallTree.root.key);

        while (currNode.isRealNode() && currNode.height > smallTree.root.getHeight()) {
            if (goRight) {
                currNode = currNode.rightSon;
            } else {
                currNode = currNode.leftSon;
            }
        }

        // We have found the node on the big tree, that has the same height as the small tree.

        AVLNode c = currNode.parent;

        if (c.getKey() > x.getKey()) {
            c.setLeft(x);
        } else {
            c.setRight(x);
        }

        if (x.getKey() < currNode.getKey()) {
            x.setRight(currNode);
            x.setLeft(smallTree.root);
        } else {
            x.setLeft(currNode);
            x.setRight(smallTree.root);
        }
        AVLNode aboveX = (AVLNode) x;

        while (aboveX.parent.isRealNode() && aboveX.parent != aboveX) {
            aboveX = aboveX.parent;
            aboveX.height = 1 + Math.max(aboveX.leftSon.height, aboveX.rightSon.height);
            aboveX.size = aboveX.leftSon.size + aboveX.rightSon.size;
            balanceTree(aboveX);
        }
        this.root = aboveX;
    }

    // Time complexity: O(log(1)))
    private AVLNode balanceTree(AVLNode root) {
        // If the tree had only one node then return
        if (root.key == -1)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = Math.max(root.leftSon.getHeight(), root.rightSon.getHeight()) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = balanceFactor(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && balanceFactor(root.leftSon) >= 0) {
            return rightRotate(root);
        }

        // Left Right Case
        if (balance > 1 && balanceFactor(root.leftSon) < 0) {
            root.setLeft(leftRotate(root.leftSon));
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && balanceFactor(root.rightSon) <= 0) {
            return leftRotate(root);
        }


        // Right Left Case
        if (balance < -1 && balanceFactor(root.rightSon) > 0) {
            root.setRight(rightRotate(root.rightSon));
            return leftRotate(root);
        }

        return root;
    }

    // Private functions
    private int balanceFactor(AVLNode y) {
        if (y == null || y.key == null) return 0;
        int leftHeight = -1;
        int rightHeight = -1;
        if (y.leftSon != null) leftHeight = y.leftSon.height;
        if (y.rightSon != null) rightHeight = y.rightSon.height;
        return leftHeight - rightHeight;
    }


    // Time complexity: O(1)
    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    private AVLNode leftRotate(AVLNode x) {
        this.numberOfRotations += 1;
        AVLNode parent = x.parent;
        boolean isRight = x.parent.isRealNode() && x.parent.rightSon.key.equals(x.key);
        AVLNode y = x.rightSon;
        AVLNode T2 = y.leftSon;

        // Perform rotation
        y.setLeft(x);
        x.setRight(T2);

        if (parent.isRealNode()) {
            if (isRight) {
                parent.setRight(y);
            } else {
                parent.setLeft(y);
            }
        } else {
            y.parent = new AVLNode();
            this.root = y;
        }


        x.size = 1 + x.leftSon.size + x.rightSon.size;
        y.size = 1 + y.leftSon.size + y.rightSon.size;

        //  Update heights
        x.height = Math.max((x.leftSon.getHeight()), x.rightSon.getHeight()) + 1;
        y.height = Math.max((y.leftSon.getHeight()), (y.rightSon.getHeight())) + 1;

        // Return new root
        return y;
    }

    // Time complexity: O(1)
    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    private AVLNode rightRotate(AVLNode y) {
        this.numberOfRotations += 1;
        boolean isRight = y.parent.isRealNode() && y.parent.rightSon.key.equals(y.key);
        AVLNode parent = y.parent;
        AVLNode x = y.leftSon;
        AVLNode T2 = x.rightSon;

        // Perform rotation
        x.setRight(y);
        y.setLeft(T2);

        if (parent.isRealNode()) {
            if (isRight) {
                parent.setRight(x);
            } else {
                parent.setLeft(x);
            }
        } else {
            x.parent = new AVLNode();
            this.root = x;
        }

        y.size = 1 + y.leftSon.size + y.rightSon.size;
        x.size = 1 + x.leftSon.size + x.rightSon.size;

        // Update heights
        y.height = Math.max((y.getLeft().getHeight()), (y.rightSon.getHeight())) + 1;
        x.height = Math.max((x.getLeft().getHeight()), (x.rightSon.getHeight())) + 1;

        // Return new root
        return x;
    }

    /**
     * public interface IAVLNode
     * ! Do not delete or modify this - otherwise all tests will fail !
     */
    public interface IAVLNode {
        public int getKey(); // Returns node's key (for virtual node return -1).

        public String getValue(); // Returns node's value [info], for virtual node returns null.

        public void setLeft(IAVLNode node); // Sets left child.

        public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.

        public void setRight(IAVLNode node); // Sets right child.

        public IAVLNode getRight(); // Returns right child, if there is no right child return null.

        public void setParent(IAVLNode node); // Sets parent.

        public IAVLNode getParent(); // Returns the parent, if there is no parent return null.

        public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.

        public void setHeight(int height); // Sets the height of the node.

        public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
    }

    /**
     * public class AVLNode
     * <p>
     * If you wish to implement classes other than AVLTree
     * (for example AVLNode), do it in this file, not in another file.
     * <p>
     * This class can and MUST be modified (It must implement IAVLNode).
     */
    public static class AVLNode implements IAVLNode {
        public Integer key;
        public String value;
        public AVLNode rightSon;
        public AVLNode leftSon;
        public Integer height = 0;
        private Integer size = 1;
        private AVLNode parent;

        public AVLNode(Integer key, String value) {
            this.key = key;
            this.value = value;
            if (this.key != null) {
                setRight(new AVLNode());
                setLeft(new AVLNode());
            } else {
                // The height for a virtual node.
                this.height = -1;
                this.size = 0;
                this.key = -1;
                this.value = null;
            }
        }

        public AVLNode() {
            this.key = -1;
            this.value = null;
            this.height = -1;
            this.size = 0;
        }

        public AVLNode(AVLNode avlNode) {
            this.key = avlNode.key;
            this.value = avlNode.value;
            this.rightSon = avlNode.rightSon;
            this.leftSon = avlNode.leftSon;
            this.size = avlNode.size;
            this.height = avlNode.height;
        }

        public int getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }

        public void setLeft(IAVLNode node) {
            if (node instanceof AVLNode) {
                this.leftSon = (AVLNode) node;
            } else {
                this.leftSon = new AVLNode();
            }
            node.setParent(this);
        }

        public IAVLNode getLeft() {
            return this.leftSon;
        }

        public void setRight(IAVLNode node) {
            if (node instanceof AVLNode) {
                this.rightSon = (AVLNode) node;
            } else {
                this.rightSon = new AVLNode();
            }
            node.setParent(this);
        }

        public IAVLNode getRight() {
            return this.rightSon;
        }

        public void setParent(IAVLNode node) {
            if (node instanceof AVLNode) {
                this.parent = (AVLNode) node;
            }
        }

        public IAVLNode getParent() {
            if (this.parent != null && this.parent.isRealNode()) {
                return this.parent;
            } else {
                return null;
            }
        }

        public boolean isRealNode() {
            return this.key != -1;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return this.height;
        }
    }




    // The following methods are for testing purposes only and
    public void bfs_print() {
        IAVLNode v = this.getRoot();
        int height = v.getHeight();
        IAVLNode[][] table = new IAVLNode[height + 1][(int) Math.pow(2, height)];

        Queue<IAVLNode> q = new ArrayDeque<>();


        q.add(v);

        for (int h = 0; h <= height; h++) {
            int levelsize = q.size();
            for (int i = 0; i < levelsize; i++) {
                v = q.remove();
                table[h][i] = v;


                if (v.isRealNode() && v.getLeft().isRealNode())
                    q.add(v.getLeft());
                else {
                    q.add(new AVLNode());
                }
                if (v.isRealNode() && v.getRight().isRealNode())
                    q.add(v.getRight());
                else {
                    q.add(new AVLNode());
                }

            }
        }
        IAVLNode[][] alignedtable = this.aligningPrintTable(table);
        String[][] treetable = this.makeTreeAlike(alignedtable);
        printtreetable(treetable);
    }


    private IAVLNode[][] aligningPrintTable(IAVLNode[][] table) {
        int height = this.root.getHeight();
        IAVLNode[][] alignedtable = new IAVLNode[height + 1][2 * ((int) Math.pow(2, height)) - 1];
        for (int i = 0; i < alignedtable.length; i++)
            for (int j = 0; j < alignedtable[0].length; j++)
                alignedtable[i][j] = null;


        for (int r = height; r >= 0; r--) {
            if (r == height) {
                for (int i = 0; i < table[0].length; i++)
                    alignedtable[r][i * 2] = table[r][i];
            } else {

                int firstloc = 0;
                int secondloc = 0;
                boolean firstNodeSeen = false;
                int currnode = 0;

                for (int j = 0; j < alignedtable[0].length; j++) {
                    if (alignedtable[r + 1][j] != null) {
                        if (firstNodeSeen) {
                            secondloc = j;
                            alignedtable[r][(firstloc + secondloc) / 2] = table[r][currnode++];
                            firstNodeSeen = false;
                        } else {
                            firstloc = j;
                            firstNodeSeen = true;
                        }
                    }
                }
            }
        }

        return alignedtable;
    }

    private String[][] makeTreeAlike(IAVLNode[][] alignedtable) {
        int height = this.root.getHeight();
        String[][] treetable = new String[(height + 1) * 3 - 2][2 * ((int) Math.pow(2, height)) - 1];

        for (int r = 0; r < treetable.length; r++) {
            if (r % 3 == 0) {
                for (int j = 0; j < treetable[0].length; j++) {
                    IAVLNode v = alignedtable[r / 3][j];
                    if (v != null && v.isRealNode()) {
                        String k = "" + v.getKey();
                        if (k.length() == 1)
                            k = k + " ";
                        treetable[r][j] = k;
                    } else {
                        if (v != null)
                            treetable[r][j] = "x ";
                        else
                            treetable[r][j] = "  ";
                    }
                }
            } else {
                if (r % 3 == 1) {
                    for (int j = 0; j < treetable[0].length; j++) {
                        if (!treetable[r - 1][j].equals("  "))
                            treetable[r][j] = "| ";
                        else
                            treetable[r][j] = "  ";
                    }
                } else { //r%3 == 2
                    continue;
                }
            }
        }
        for (int r = 0; r < treetable.length; r++) {
            if (r % 3 == 2) {
                boolean write = false;
                for (int j = 0; j < treetable[0].length; j++) {
                    if (!treetable[r + 1][j].equals("  ")) {
                        if (write)
                            treetable[r][j] = "__";
                        write = !write;
                    }
                    if (write)
                        treetable[r][j] = "__";
                    else
                        treetable[r][j] = "  ";
                }
            }
        }


        return treetable;
    }

    private void printtreetable(String[][] treetable) {
        for (int i = 0; i < treetable.length; i++) {
            for (int j = 0; j < treetable[0].length; j++) {
                System.out.print(treetable[i][j]);
                if (j == treetable[0].length - 1)
                    System.out.print("\n");
            }
        }
    }
}



