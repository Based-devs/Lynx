package me.wolfsurge.cerauno.event;

/**
 * A basic event
 *
 * @author Wolfsurge
 * @since 05/03/22
 */
public class Event {
    // The state of the event
    private final State state;

    public Event(State state) {
        this.state = state;
    }

    /**
     * Gets the state of the event
     *
     * @return The state of the event
     */
    public State getState() {
        return this.state;
    }

    public enum State {
        /**
         * Before something happens
         */
        PRE,

        /**
         * After something happens
         */
        POST,

        /**
         * During something happening
         */
        PERI
    }
}
