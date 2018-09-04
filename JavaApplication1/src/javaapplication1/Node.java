/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;

/**
 *
 * @author student
 */
public class Node {
    
    float data;
    Vector<Node> child;
    Timestamp timestamp;
    String ownerName;
    Integer ownerId;
    String hashData;
    Integer nodeNumber=0;
    String nodeId;
    String referenceNodeId;
    String childReferenceNodeId;
    String genesisReferenceNodeId;
    String hashValue;   //Hash Value of the whole set
    
    public Node(float d) 
    {
        data = d;
        child = null;
        timestamp = new Timestamp(System.currentTimeMillis());
        ++nodeNumber;
        nodeId = UUID.randomUUID().toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

static float sumNodes(Node root)
{
    float sum = 0;
    if(root.child==null)
        return 0;
    
    for(int i=0;i<root.child.size();i++)
        sum+=root.child.get(i).data;
    
    return sum;
}    

static void addNode(Node root, float[] keys){
    if(root==null){
        System.out.println("No node exists");
        return;
    }
    
    float sum = sumNodes(root);
    for(int i = 0; i<keys.length; i++){
        sum += keys[i];
    }
    
    if(sum>root.data){
        System.out.println("Condition violated");
    } else{
        root.child=new Vector<>();
        for(int j = 0; j<keys.length; j++)
            root.child.add(new Node(keys[j]));
        System.out.println("Nodes successfully inserted");
    }
}

}

