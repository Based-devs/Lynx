package dev.based.vampyrix.api.event.network;

import dev.based.vampyrix.api.event.ClientEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends ClientEvent {
    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        super(true);
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public static class Send extends PacketEvent {
        public Send(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Receive extends PacketEvent {
        public Receive(Packet<?> packet) {
            super(packet);
        }
    }
}