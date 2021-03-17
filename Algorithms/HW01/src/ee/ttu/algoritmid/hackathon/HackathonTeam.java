/*
  Event
 */
package ee.ttu.algoritmid.hackathon;
/**
 * Interface for the team.
 */
public interface HackathonTeam {

    /**
     * @return Participant
     */
    HackathonParticipant getDeveloper();

    /**
     * @return Participant
     */
    HackathonParticipant getBusinessAnalyst();

}
