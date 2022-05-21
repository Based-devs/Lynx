package com.based.lynx.util;

import net.minecraft.util.math.MathHelper;

/**
 * @author Tigermouthbear, linustouchtips
 * @since 06/08/2021 //rewrite this so its not skidded -Master7720
 */
public class Animation {

    // animation time
    public float time;

    // animation current state
    private State currentState = State.STATIC;
    private long currentStateStart = 0;

    // animation previous state
    private State previousState = State.STATIC;
    private boolean initialState;

    public Animation(int time, boolean initialState) {
        this.time = time;
        this.initialState = initialState;

        // start as expanded
        if (initialState) {
            previousState = State.EXPANDING;
        }
    }

    /**
     * Gets the animation length (0 to 1)
     *
     * @return The animation length (0 to 1)
     */
    public float getAnimationFactor() {
        if (currentState.equals(State.EXPANDING)) {
            return MathHelper.clamp((System.currentTimeMillis() - currentStateStart) / time, 0, 1);
        }

        if (currentState.equals(State.RETRACTING)) {
            return MathHelper.clamp(((long) time - (System.currentTimeMillis() - currentStateStart)) / time, 0, 1);
        }

        return previousState.equals(State.EXPANDING) ? 1 : 0;
    }

    /**
     * Gets the initial state
     *
     * @return The initial state
     */
    public boolean getState() {
        return initialState;
    }

    /**
     * Sets the state
     *
     * @param expand Expand or retract
     */
    public void setState(boolean expand) {
        if (expand) {
            currentState = State.EXPANDING;
            initialState = true;
        } else {
            currentState = State.RETRACTING;
        }

        // reset time
        currentStateStart = System.currentTimeMillis();
    }

    /**
     * Sets the state (no animation)
     *
     * @param expand Expand or retract
     */
    public void setStateHard(boolean expand) {
        if (expand) {
            currentState = State.EXPANDING;
            initialState = true;

            // reset time
            currentStateStart = System.currentTimeMillis();
        } else {
            previousState = State.RETRACTING;
            currentState = State.RETRACTING;
            initialState = false;
        }
    }

    public enum State {

        /**
         * Expands the animation
         */
        EXPANDING,

        /**
         * Retracts the animation
         */
        RETRACTING,

        /**
         * No animation
         */
        STATIC
    }
}