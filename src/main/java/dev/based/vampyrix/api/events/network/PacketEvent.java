package dev.based.vampyrix.api.events.network;

import me.wolfsurge.cerauno.event.CancellableEvent;
import net.minecraft.network.Packet;

//Packet event
public class PacketEvent extends CancellableEvent {
    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
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