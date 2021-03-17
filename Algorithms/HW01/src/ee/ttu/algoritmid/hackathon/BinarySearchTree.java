package ee.ttu.algoritmid.hackathon;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a binary search tree.
 */
public class BinarySearchTree {

    /**
     * Implementation of a binary search trees node.
     */
    static class Node {

        /**
         * Participant.
         */
        private HackathonParticipant participant;
        /**
         * Left, right nodes.
         */
        private Node left, right;

        /**
         * Node constructor.
         *
         * @param  participant  Person Object.
         */
        Node(final HackathonParticipant participant) {
            this.participant = participant;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * Top root node.
     */
    private Node root;

    /**
     * Best match variable.
     */
    private HackathonParticipant bestChoice;

    /**
     * Best difference variable for finding the best match.
     */
    private double bestDiff;

    /**
     * Max diff.
     */
    private static final double MAX_DIFF = 7.5;

    /**
     * List to return everyone who is in-line waiting.
     */
    private List<HackathonParticipant> waitList = new ArrayList<>();

    /**
     * Constructor.
     */
    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Registers participant to the waiting line.
     *
     * @param  participant  Person Object.
     */
    public void insert(final HackathonParticipant participant) {
        this.root = insertRec(this.root, participant);
    }

    /**
     * Recursive method to add the person to the tree.
     *
     * @param   root  Root node.
     * @param   participant  Participant.
     * @return  Node   Returns added tree node.
     */
    public Node insertRec(final Node root,
                          final HackathonParticipant participant) {
        if (root == null) {
            return new Node(participant);
        }

        double newPersonLevel = participant.getExtroversionLevel();
        double rootLevel = root.participant.getExtroversionLevel();

        if (newPersonLevel < rootLevel) {
            root.left = insertRec(root.left, participant);
        } else if (newPersonLevel >= rootLevel) {
            root.right = insertRec(root.right, participant);
        }
        return root;
    }

    /**
     * Searches for the best match for a new participant if there are any.
     * Removes from line if there is a match.
     *
     * @param   participant  Participant.
     * @return  HackathonParticipant   Returns the best match or null.
     */
    public HackathonParticipant search(final HackathonParticipant participant) {
        this.bestDiff = MAX_DIFF;
        searchRec(root, participant);
        HackathonParticipant bestTeamMate = this.bestChoice;
        if (bestTeamMate != null) {
            deleteParticipant(bestTeamMate);
        }
        return bestTeamMate;
    }

    /**
     * Recursive method to search the best match for a new participant.
     *
     * @param   root  Root node.
     * @param   participant  Participant.
     */
    public void searchRec(final Node root,
                          final HackathonParticipant participant) {
        if (root != null) {
            if (root.participant != participant) {
                double newPersonLevel = participant.getExtroversionLevel();
                double rootLevel = root.participant.getExtroversionLevel();
                double diff = Math.abs(rootLevel - newPersonLevel);
                HackathonParticipant.Role newPersonRole = participant.getRole();
                HackathonParticipant.Role rootRole = root.participant.getRole();
                if (diff < this.bestDiff && rootRole != newPersonRole) {
                    this.bestChoice = root.participant;
                    this.bestDiff = diff;
                } else if (diff == this.bestDiff) {
                    if (rootRole != newPersonRole) {
                        if (bestChoice == null
                                || rootLevel
                                < bestChoice.getExtroversionLevel()) {
                            this.bestChoice = root.participant;
                            this.bestDiff = diff;
                        }
                    }
                }
                if (newPersonLevel > rootLevel) {
                    searchRec(root.right, participant);
                } else {
                    searchRec(root.left, participant);
                }
            }
        }
    }

    /**
     * Deletes participant from the waiting line.
     *
     * @param  participant  Person Object.
     */
    private void deleteParticipant(final HackathonParticipant participant) {
        this.root = deleteRec(root, participant);
        this.bestChoice = null;
    }

    /**
     * Recursive method to remove the person to the tree.
     *
     * @param root  Root node.
     * @param participant Participant.
     * @return  Node   Returns changed tree node.
     */
    private Node deleteRec(final Node root,
                           final HackathonParticipant participant) {
        // If the tree is empty
        if (root == null) {
            return null;
        }

        double personToDeleteLevel = participant.getExtroversionLevel();
        double rootLevel = root.participant.getExtroversionLevel();
        // Otherwise, recur down the tree
        if (participant != root.participant
                && personToDeleteLevel < rootLevel) {
            root.left = deleteRec(root.left, participant);
        } else if (participant != root.participant
                && personToDeleteLevel > rootLevel
                || participant != root.participant
                && personToDeleteLevel == rootLevel) {
            root.right = deleteRec(root.right, participant);
        } else if (participant == root.participant) {
            // if key is same as root's key, then This is the node
            // to be deleted

            // node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.participant = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.participant);
        }

        return root;
    }

    /**
     * Method to find the new Root node.
     *
     * @param   root  Tree node.
     * @return  Node   Returns new tree root node.
     */
    private HackathonParticipant minValue(Node root) {
        HackathonParticipant minv = root.participant;
        while (root.left != null) {
            minv = root.left.participant;
            root = root.left;
        }
        return minv;
    }

    /**
     * Method to create the in-line wait list in the correct order.
     */
    private void inorder()  {
        this.waitList = new ArrayList<>();
        inorderRec(this.root);
    }

    /**
     * Method to add the participants in-line to the list.
     *
     * @param   root  Tree node.
     */
    private void inorderRec(final Node root) {
        if (root != null) {
            inorderRec(root.left);
            if (!this.waitList.contains(root.participant)) {
                this.waitList.add(root.participant);
            }
            inorderRec(root.right);
        }
    }

    /**
     * Method to return the in-line wait list.
     *
     * @return  List<HackathonParticipant>   Returns a list of participants.
     */
    public List<HackathonParticipant> getWaitList() {
        inorder();
        return waitList;
    }
}
