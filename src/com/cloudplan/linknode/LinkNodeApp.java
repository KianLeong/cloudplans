package com.cloudplan.linknode;

import java.util.ArrayList;
import java.util.List;

public class LinkNodeApp {
    public static void main(String[] args) {
        //This method prepares the tree data. You may edit the tree here
        Node rootnode = prepareTree();
        List<Node> levelright = new ArrayList<>();

        //This method sets the right property of each node
        populateRight(rootnode);

        //This method prints node using the Right property in each level
        visitLeftMostChild(rootnode);

    }

    private static Node prepareTree() {
        Node rootNode = new Node("root");

        Node childnode1 = new Node("child1");
        Node childnode2 = new Node("child2");
        Node childnode3 = new Node("child3");
        Node childnode4= new Node("child4");

        rootNode.children.add(childnode1);
        rootNode.children.add(childnode2);
        rootNode.children.add(childnode3);
        rootNode.children.add(childnode4);

        Node grandnode1 = new Node("grandchild1");
        Node grandnode2 = new Node("grandchild2");
        childnode1.children.add(grandnode1);
        childnode1.children.add(grandnode2);

        Node grandnode3 = new Node("grandchild3");
        childnode4.children.add(grandnode3);

        /*
        Node greatgrandnode1=new Node("ggrandchild1");
        grandnode1.children.add(greatgrandnode1);

        Node greatgrandnode2=new Node("ggrandchild2");
        grandnode3.children.add(greatgrandnode2);

        Node greatgrandnode3=new Node("ggrandchild3");
        grandnode3.children.add(greatgrandnode3);
        */
        return rootNode;
    }

    private static void populateRight(Node node) {
        Node currentnode = node;
        Node childlevelmostright = null;
        Node childlevelmostleft = null;
        //The solution is to solved from root and iterate through the children for populating right attribute. The solution at the child layer will be used for resolving the grand child layer (Dynamic Programming)
        while (currentnode != null) {
            for (Node childnode : currentnode.children) {
                if (childlevelmostleft == null) {
                    childlevelmostleft = childnode;
                }
                if (childlevelmostright != null) {
                    childlevelmostright.right = childnode;
                }
                childlevelmostright = childnode;
            }
            //If there is no more node in the current node right, go to the next level leftmost node
            if (currentnode.right == null) {
                if (childlevelmostleft != null) {
                    currentnode = childlevelmostleft;
                    //reset the variables back to null as the next level of nodes are being read
                    childlevelmostleft = null;
                    childlevelmostright = null;
                } else {
                    //If no left most child is found for this layer, the leaf layer has been reached
                    break;
                }
            }
            //else go to its right node
            else {
                currentnode = currentnode.right;
            }
        }
    }
    /*
    private static void populateRight(Node node, int i, List<Node> levelright) {
        if (!(levelright.size()>i)){
            levelright.add(node);
        }
        else{
            Node currrightnode=levelright.get(i);
            currrightnode.right=node;
            levelright.set(i,node);
        }
        for (Node child:node.children){
            populateRight(child,i+1,levelright);
        }
    }
    */

    //This method prints each level most left child node
    private static void visitLeftMostChild(Node node) {
        System.out.print("Nodes in this level (from left to right):");
        printRightNode(node);
        System.out.println();

        while (true) {
            //If node has child, visit its child, else go to its right node to check for child
            if (node.children.size() > 0) {
                visitLeftMostChild(node.children.get(0));
                //break the loop since node with child has been found
                break;
            }
            if (node.right!=null)
                node = node.right;
            else{
                //break the loop since all the nodes at this level have no child
                break;
            }
        }

    }

    private static void printRightNode(Node node) {
        System.out.print(node.name + " ");
        if (node.right != null) {
            printRightNode(node.right);
        }
    }
}
