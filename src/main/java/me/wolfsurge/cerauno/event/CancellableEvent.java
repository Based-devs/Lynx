package me.wolfsurge.cerauno.event;

/**
 * A basic cancellable event that extends off of {@link Event} the event class
 * @author Wolfsurge
 * @since 05/03/22
 */
public class CancellableEvent extends Event {

    private boolean cancelled;

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

}
