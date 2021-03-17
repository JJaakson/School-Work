package ee.ttu.algoritmid.hackathon;
import java.util.List;

/**
 * Interface for the registration system.
 */
public interface HackathonRegistrationSystem {

    /**
     * Registers participant to the hackathon.
     *
     * @param  participant  Person Object.
     * @return  HackathonTeam   Team that contains a developer and an analyst.
     * @throws IllegalArgumentException if participant contains wrong variables.
     */
    HackathonTeam registerToHackaton(HackathonParticipant participant)
            throws IllegalArgumentException;

    /**
     * Returns a list with in-line participants.
     *
     * @return  List<HackathonParticipant>  In-line participants.
     */
    List<HackathonParticipant> participantsWithoutTeam();
}
