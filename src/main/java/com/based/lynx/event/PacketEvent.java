package com.based.lynx.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketEvent
        extends Event {
    private Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public static class PostSend
            extends PacketEvent {
        public PostSend(Packet<?> packet) {
            super(packet);
        }
    }

    public static class PostReceive
            extends PacketEvent {
        public PostReceive(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Send
            extends PacketEvent {
        public Send(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Receive
            extends PacketEvent {
        public Receive(Packet<?> packet) {
            super(packet);
        }
    }
}
