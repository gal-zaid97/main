package main.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class NewTest5 {
    public static void main(String[] args) {
        randomJoin();
    }

    public static void randomJoin() {
        AVLTree avlTree1 = new AVLTree();
        int range = 2000;
//        AVLTree avlTree2 = new AVLTree();
//        AVLTree avlTree3 = new AVLTree();
//        AVLTree avlTree4 = new AVLTree();
//        AVLTree avlTree5 = new AVLTree();
//        AVLTree avlTree6 = new AVLTree();
//        AVLTree avlTree7 = new AVLTree();
//        AVLTree avlTree8 = new AVLTree();
//        AVLTree avlTree9 = new AVLTree();
//        AVLTree avlTree10 = new AVLTree();
        Integer[] arr1 = new Integer[range];
//        Integer[] arr2 = new Integer[1000 * 2 ^ j];
//        Integer[] arr3 = new Integer[1000 * 2 ^ j];
//        Integer[] arr4 = new Integer[1000 * 2 ^ j];
//        Integer[] arr5 = new Integer[1000 * 2 ^ j];
//        Integer[] arr6 = new Integer[1000 * 2 ^ j];
//        Integer[] arr7 = new Integer[1000 * 2 ^ j];
//        Integer[] arr8 = new Integer[1000 * 2 ^ j];
//        Integer[] arr9 = new Integer[1000 * 2 ^ j];
//        Integer[] arr10 = new Integer[1000 * 2 ^ j];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = i;
//            arr2[i] = i;
//            arr3[i] = i;
//            arr4[i] = i;
//            arr5[i] = i;
//            arr6[i] = i;
//            arr7[i] = i;
//            arr8[i] = i;
//            arr9[i] = i;
//            arr10[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr1));
//        Collections.shuffle(Arrays.asList(arr2));
//        Collections.shuffle(Arrays.asList(arr3));
//        Collections.shuffle(Arrays.asList(arr4));
//        Collections.shuffle(Arrays.asList(arr5));
//        Collections.shuffle(Arrays.asList(arr6));
//        Collections.shuffle(Arrays.asList(arr7));
//        Collections.shuffle(Arrays.asList(arr8));
//        Collections.shuffle(Arrays.asList(arr9));
//        Collections.shuffle(Arrays.asList(arr10));
        avlTree1.averageJoin = 0;
        avlTree1.maxJoin = 0;
        for (int i = 0; i < arr1.length; i++) {
            avlTree1.insert(arr1[i], "");
//            avlTree2.insert(arr2[i], "");
//            avlTree3.insert(arr3[i], "");
//            avlTree4.insert(arr4[i], "");
//            avlTree5.insert(arr5[i], "");
//            avlTree6.insert(arr6[i], "");
//            avlTree7.insert(arr7[i], "");
//            avlTree8.insert(arr8[i], "");
//            avlTree9.insert(arr9[i], "");
//            avlTree10.insert(arr10[i], "");
        }
        int random = getRandomNumberUsingInts(0, range);
        AVLTree[] arr = avlTree1.split(random);
//        avlTree2.split(random);
//        avlTree3.split(random);
//        avlTree4.split(random);
//        avlTree5.split(random);
//        avlTree6.split(random);
//        avlTree7.split(random);
//        avlTree8.split(random);
//        avlTree9.split(random);
//        avlTree10.split(random);

        double averageJoin1 = (avlTree1.averageJoin);
        double maxJoin1 = (avlTree1.maxJoin);

        avlTree1.averageJoin = 0.0;
        avlTree1.maxJoin = 0;
        avlTree1.split(getLeftSonLargestNode(avlTree1).key);
//        avlTree2.split(getLeftSonLargestNode(avlTree1).key);
//        avlTree3.split(getLeftSonLargestNode(avlTree1).key);
//        avlTree4.split(getLeftSonLargestNode(avlTree1).key);
//        avlTree5.split(getLeftSonLargestNode(avlTree1).key);
//        avlTree6.split(getLeftSonLargestNode(avlTree1).key);
//        avlTree7.split(getLeftSonLargestNode(avlTree1).key);
//        avlTree8.split(getLeftSonLargestNode(avlTree1).key);
//        avlTree9.split(getLeftSonLargestNode(avlTree1).key);
//        avlTree10.split(getLeftSonLargestNode(avlTree1).key);

        double averageJoin2 = (avlTree1.averageJoin);
        double maxJoin2 = (avlTree1.maxJoin);
        System.out.println("Average join 1: " + averageJoin1);
        System.out.println("Max join 1: " + maxJoin1);
        System.out.println("Average join 2: " + averageJoin2);
        System.out.println("Max join 2: " + maxJoin2);
    }

    public static int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    public static AVLTree.AVLNode getLeftSonLargestNode(AVLTree tree) {
        AVLTree.AVLNode node = (AVLTree.AVLNode) tree.getRoot();
        AVLTree.AVLNode curr = node.leftSon;

        while (curr.rightSon.isRealNode()) {
            curr = curr.rightSon;
        }
        return curr;
    }
}
