package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NewTest4 {

    public static void main(String[] args) {
        Integer[] arr2 = new Integer[32000];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr2));
        AVLTree avlTree = new AVLTree();
        int total = 2;
        for (int i = 0; i < arr2.length; i++) {
            avlTree.insert(arr2[i], "");
            if (i > 1) {
                AVLTree.AVLNode max = avlTree.maxValueNode((AVLTree.AVLNode) avlTree.getRoot());
                AVLTree.AVLNode curr = avlTree.maxValueNode((AVLTree.AVLNode) avlTree.getRoot());
                AVLTree currTree = new AVLTree(max);
                while (curr != null && curr.getParent() != null && curr.getParent().isRealNode()) {
                    if (currTree.search(arr2[i]) != null) {
                        total += currTree.getRoot().getRight().getHeight() + currTree.getRoot().getLeft().getHeight() + 2;
                        break;
                    }
                    curr = (AVLTree.AVLNode) curr.getParent();
                    currTree = new AVLTree(curr);
                }
            }
        }
        System.out.println(total);
    }
}