package ee.ttu.algoritmid.hackathon;
import java.util.List;

/**
 * Registration system.
 */
public class HW01 implements HackathonRegistrationSystem {

    /**
    * Implemented binary search tree.
    */
   private final BinarySearchTree binarySearchTree = new BinarySearchTree();

   /**
    * Registers participant to the hackathon.
    *
    * @param  participant  Person Object.
    * @return  HackathonTeam   Team that contains a developer and an analyst.
    * @throws IllegalArgumentException if participant contains wrong variables.
    */
   @Override
   public HackathonTeam registerToHackaton(
           final HackathonParticipant participant)
           throws IllegalArgumentException {
       if (participant == null
               || participant.getName() == null
               || participant.getName().isBlank()
               || participant.getRole() == null
               || participant.getExtroversionLevel() < 0) {
           throw new IllegalArgumentException();
       }
       HackathonParticipant bestChoice = binarySearchTree.search(participant);
       if (bestChoice != null) {
           if (participant.getRole() == HackathonParticipant.Role.DEVELOPER) {
               return new Team(participant, bestChoice);
           } else  {
               return new Team(bestChoice, participant);
           }
       }
       binarySearchTree.insert(participant);
       return null;
   }

    /**
     * Returns a list with in-line participants.
     *
     * @return  List<HackathonParticipant>  In-line participants.
     */
   @Override
   public List<HackathonParticipant> participantsWithoutTeam() {
       return binarySearchTree.getWaitList();
   }
}
