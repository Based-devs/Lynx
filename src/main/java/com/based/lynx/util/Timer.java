package com.based.lynx.util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Timer {

    public long milliseconds;

    public Timer() {
        milliseconds = -1;

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(LivingEvent.LivingUpdateEvent event) {
        if (Wrapper.getWorld() == null || Wrapper.getPlayer() == null) {
            milliseconds = -1;
        }
    }

    public boolean hasTimePassed(long time, TimeFormat format) {
        switch (format) {
            case MILLISECONDS:
                return (System.currentTimeMillis() - milliseconds) >= time;
            case SECONDS:
                return (System.currentTimeMillis() - milliseconds) >= (time * 1000);
        }

        return false;
    }

    public void reset() {
        milliseconds = System.currentTimeMillis();
    }

    public enum TimeFormat {
        /**
         * Time in milliseconds
         */
        MILLISECONDS,

        /**
         * Time in seconds
         */
        SECONDS
    }

}
