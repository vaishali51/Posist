/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import static java.lang.Integer.max;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
    String[] childReferenceNodeId;
    static String genesisReferenceNodeId;
    String hashValue;   //Hash Value of the whole set
    private String encryptedData;
    
    public Node(float d, String ownerName, Integer ownerId) 
    {
        data = d;
        child = null;
        timestamp = new Timestamp(System.currentTimeMillis());
        ++nodeNumber;
        nodeId = UUID.randomUUID().toString();
        this.ownerId=ownerId;
        this.ownerName=ownerName;
        hashData = generateHashData(data, this.ownerId, this.ownerName);
        encryptedData = encryption(d, ownerName, ownerId, hashData);
    }
    
    /* Concept of hash value is same as hashData */
    
    static void createGenesisNode(float d, String ownerName, Integer ownerId){
        Node genesisNode = new Node(d,ownerName,ownerId);
        genesisReferenceNodeId=genesisNode.nodeId;
    } 
    
    int depthOfTree(Node root)
{
    //longest chain finding method
    
    // Base case
    if (root==null)
        return 0;
 
    // Check for all children and find
    // the maximum depth
    int maxdepth = 0;
    for (int i=0;i<root.child.size();i++)
        maxdepth = max(maxdepth, depthOfTree(root.child.get(i)));
 
    return maxdepth + 1 ;
}
    

    static String encryption(float adata, String ownerName, Integer ownerId, String hashData) {
        try {
            byte[] keyBytes=null;
byte[] ivBytes=null;
            String data = adata + ownerId + ownerName + hashData;
            SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
// create the cipher with the algorithm you choose
// see javadoc for Cipher class for more info, e.g.
Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
byte[] encrypted= new byte[cipher.getOutputSize(data.getBytes().length)];
int enc_len = cipher.update(data.getBytes(), 0, data.getBytes().length, encrypted, 0);
enc_len += cipher.doFinal(encrypted, enc_len);
return encrypted.toString();
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ShortBufferException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return null;
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

//Takes 2 parameters - parent node and values of the children in array to be inserted to that node
static void addNode(Node root, float[] keys, String[] ownerNames, Integer[] ownerIds){
    if(root==null){
        System.out.println("No node exists");
        return;
    }
    
    if(keys.length!=ownerNames.length && ownerIds.length!=keys.length && ownerNames.length!=ownerIds.length)
        return;
    
    float sum = sumNodes(root);
    for(int i = 0; i<keys.length; i++){
        sum += keys[i];
    }
    
    if(sum>root.data){
        System.out.println("Condition violated");
    } else{
        root.child=new Vector<>();
        root.childReferenceNodeId = new String[keys.length];
        for(int j = 0; j<keys.length; j++){
            Node n = new Node(keys[j], ownerNames[j], ownerIds[j]);
            root.child.add(n);
            n.referenceNodeId=root.nodeId;
            n.childReferenceNodeId=null;
            root.childReferenceNodeId[j]=n.nodeId;
        }
        System.out.println("Nodes successfully inserted");
    }
}

    private String generateHashData(float data, Integer ownerId, String ownerName) {
        String str = String.valueOf(String.valueOf(data).charAt(0)) + String.valueOf(String.valueOf(ownerId).charAt(0)) + String.valueOf(ownerName.charAt(0));
        return String.valueOf(str.hashCode());
    }

    
}

