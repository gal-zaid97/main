package main.java;

public class NewTest3 {

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        int count = 6;
        int n = 2000;
        for (int i = n; i > 0; i--) {
//            if (!avlTree.empty())
//                avlTree.bfs_print();
            int insertCnt = avlTree.insert(i, "");
            if (i < n - 2) {
                int a = (avlTree).distanceFromMin() + avlTree.distanceFromMax() + 1;
                count += a;
                AVLTree.AVLNode node = avlTree.getItemWithKey(i);
                if (node.rightSon.isRealNode() || node.leftSon.isRealNode()) {
                    count += 1;
                }
            }
        }
        System.out.println(count);
    }
}
