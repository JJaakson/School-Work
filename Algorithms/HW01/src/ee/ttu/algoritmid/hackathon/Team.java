package ee.ttu.algoritmid.hackathon;

/**
 * Implemented Team class.
 */
public class Team implements HackathonTeam {

    /**
     * Developer.
     */
    private final HackathonParticipant developer;
    /**
     * Analyst.
     */
    private final HackathonParticipant analyst;

    /**
     * Constructor.
     *
     * @param developer Person.
     * @param analyst Person.
     */
    public Team(final HackathonParticipant developer,
                final HackathonParticipant analyst) {
        this.developer = developer;
        this.analyst = analyst;
    }

    /**
     * @return Participant
     */
    @Override
    public HackathonParticipant getDeveloper() {
        return this.developer;
    }

    /**
     * @return Participant
     */
    @Override
    public HackathonParticipant getBusinessAnalyst() {
        return this.analyst;
    }
}
