package ee.ttu.algoritmid.subtreesum; 
/**  
 * Please don't change this class, it is only for your reference (it will be overridden by the tester anyways)  
 */  
public class Node {  

    private Node left = null;  
    private Node right = null;  
    private long value;  
    private long sumOfAllRight = 0;  
    private long sumOfAllChildren = 0;  

    public Node(long value) {  
        this.value = value;  
    }  

    public Node getLeft() {  
        return left;  
    }  

    public void setLeft(Node left) {  
        this.left = left;  
    }  

    public Node getRight() {  
        return right;  
    }  

    public void setRight(Node right) {  
        this.right = right;  
    }  

    public long getSumOfAllRight() {  
        return sumOfAllRight;  
    }  

    public void setSumOfAllRight(long sumOfAllRight) {  
        this.sumOfAllRight = sumOfAllRight;  
    }  

    public long getValue() {  
        return value;  
    }  

    public void setValue(long value) {  
        this.value = value;  
    }  

    public long getSumOfAllChildren() {  
        return sumOfAllChildren;  
    }  

    public void setSumOfAllChildren(long sumOfAllChildren) {  
        this.sumOfAllChildren = sumOfAllChildren;  
    }  

}  
