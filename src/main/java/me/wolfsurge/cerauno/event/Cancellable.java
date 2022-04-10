package me.wolfsurge.cerauno.event;

/**
 * A basic cancellable event that extends off of {@link Event} the event class
 *
 * @author Wolfsurge
 * @since 05/03/22
 */
public class Cancellable extends Event {
    private boolean cancelled = false;

    public Cancellable(State state) {
        super(state);
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
