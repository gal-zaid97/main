package main.java;

public class NewTest2 {

    public static void main(String[] args) {

        AVLTree avlTree1 = new AVLTree();

        avlTree1.insert(5, "gg");
        avlTree1.insert(6, "gg");
        avlTree1.insert(7, "gg");
        avlTree1.insert(8, "gg");
        avlTree1.insert(9, "gg");
        avlTree1.insert(10, "gg");
        avlTree1.insert(11, "gg");


        AVLTree[] arr = avlTree1.split(8);
        System.out.println("");

    }
}
