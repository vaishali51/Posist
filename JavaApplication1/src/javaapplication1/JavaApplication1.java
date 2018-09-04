/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.Vector;

/**
 *
 * @author student
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Node root = new Node(5, "d", 3);
        root.child = new Vector<>();
        root.child.add(new Node(6, "d", 3));
        root.child.add(new Node(7, "d", 3));
        root.child.add(new Node(8, "d", 3));
        System.out.println(root.nodeId);
        System.out.println(root.child.get(2).nodeId);
        System.out.println(root.child.get(2).data);
        System.out.println(Node.sumNodes(root));
        Node.addNode(root.child.get(2), new float[]{2,9}, new String[]{"V","H"}, new Integer[]{1,2});
        System.out.println(Node.sumNodes(root.child.get(2)));
    }
    
}
