package dev.based.vampyrix.api.event.network;

import me.wolfsurge.cerauno.event.Cancellable;
import net.minecraft.network.Packet;

public class PacketEvent extends Cancellable {
    private final Packet<?> packet;

    public PacketEvent(State state, Packet<?> packet) {
        super(state);
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public static class Send extends PacketEvent {
        public Send(Packet<?> packet) {
            super(State.PRE, packet);
        }
    }

    public static class Receive extends PacketEvent {
        public Receive(Packet<?> packet) {
            super(State.POST, packet);
        }
    }
}