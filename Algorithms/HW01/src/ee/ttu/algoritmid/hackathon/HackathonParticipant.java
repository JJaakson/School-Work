package ee.ttu.algoritmid.hackathon;

/**
 * Interface for participants.
 */
public interface HackathonParticipant {

    /**
     * Enumerate to differentiate different types of participants.
     */
     enum Role {

        /**
         * Developer type.
         */
        DEVELOPER,

        /**
         * Analyst type.
         */
        BUSINESS_ANALYST

    }

    /**
     * To access the participants name.
     *
     * @return  String
     */
    String getName();

    /**
     * To access the participants Role.
     *
     * @return  Role
     */
    Role getRole();

    /**
     * To access the participant extroverison level.
     *
     * @return  double
     */
    double getExtroversionLevel();
}
