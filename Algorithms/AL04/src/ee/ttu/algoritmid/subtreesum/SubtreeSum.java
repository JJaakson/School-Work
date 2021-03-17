package ee.ttu.algoritmid.subtreesum;

public class SubtreeSum {

    /**  
     * Calculate sum of all right children for every node  
     * @param rootNode root node of the tree. Use it to traverse the tree.  
     * @return root node of the tree where for every node is computed sum of it's all right children  
     */  
    public Node calculateRightSums(Node rootNode) {
        Node leftNode = rootNode.getLeft();
        Node rightNode = rootNode.getRight();
        if (leftNode == null && rightNode == null) {
            return rootNode;
        }
        if (leftNode != null && rightNode == null) {
            rootNode.setSumOfAllChildren(calculateRightSums(leftNode).getSumOfAllChildren()
                    + leftNode.getValue());
        } else if (leftNode == null) {
            rootNode.setSumOfAllChildren(calculateRightSums(rightNode).getSumOfAllChildren()
                    + rightNode.getValue());
            rootNode.setSumOfAllRight(rightNode.getSumOfAllChildren() + rightNode.getValue());
        } else {
            rootNode.setSumOfAllChildren(calculateRightSums(leftNode).getSumOfAllChildren()
                    + leftNode.getValue()
                    + calculateRightSums(rightNode).getSumOfAllChildren()
                    + rightNode.getValue());
            rootNode.setSumOfAllRight(rightNode.getSumOfAllChildren() + rightNode.getValue());
        }
        return rootNode;
    }  

    public static void main(String[] args) throws Exception {
        /**
         *  Use this example to test your solution
         *                  Tree:
         *                   15
         *               /       \
         *             10         17
         *           /   \       /  \
         *         3     13     5    25
         */
        Node rootNode = new Node(15);
        Node a = new Node(10);
        Node b = new Node(17);
        Node c = new Node(3);
        Node d = new Node(13);
        Node e = new Node(5);
        Node f = new Node(25);
        Node g = new Node(6);
        Node h = new Node(18);
        Node j = new Node(8);

        rootNode.setLeft(a);
        rootNode.setRight(b);
        a.setLeft(c);
        a.setRight(d);
        b.setLeft(e);
        b.setRight(f);
        f.setRight(g);
        g.setLeft(h);
        g.setRight(j);


        SubtreeSum solution = new SubtreeSum();
        solution.calculateRightSums(rootNode);
        System.out.println(rootNode.getSumOfAllRight());
        System.out.println(rootNode.getSumOfAllChildren());
        System.out.println(b.getSumOfAllRight());
        System.out.println(b.getSumOfAllChildren());
        System.out.println(g.getSumOfAllRight());
        System.out.println(g.getSumOfAllChildren());
        if (rootNode.getSumOfAllRight() != 47 ||
                a.getSumOfAllRight() != 13 ||
                b.getSumOfAllRight() != 25 ||
                c.getSumOfAllRight() != 0) {
            throw new Exception("There is a mistake in your solution.");
        }

        System.out.println("Your solution should be working fine in basic cases, try to push.");

    }

}
