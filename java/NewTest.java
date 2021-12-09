package main.java;

public class NewTest {


    public static void main(String[] args){
        AVLTree avlTree = new AVLTree();
        avlTree.insert(12,"");
        avlTree.insert(1,"");
        avlTree.insert(15,"gg");
        avlTree.insert(9,"");
        avlTree.insert(7,"");
        avlTree.insert(8,"");
        avlTree.insert(11,"");
        avlTree.insert(20,"");
        avlTree.insert(21,"");
        avlTree.insert(22,"");
        avlTree.delete(22);
        System.out.println("");

    }

}
