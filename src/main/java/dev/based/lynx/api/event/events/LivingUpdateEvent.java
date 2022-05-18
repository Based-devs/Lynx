package dev.based.lynx.api.event.events;

import dev.based.lynx.api.event.ClientEvent;
import net.minecraft.entity.EntityLivingBase;

public class LivingUpdateEvent extends ClientEvent {
    private static final LivingUpdateEvent INSTANCE = new LivingUpdateEvent();

    private EntityLivingBase entity;

    public static LivingUpdateEvent get(EntityLivingBase entity) {
        INSTANCE.entity = entity;

        return INSTANCE;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }
}
