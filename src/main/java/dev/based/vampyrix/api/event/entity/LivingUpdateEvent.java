package dev.based.vampyrix.api.event.entity;

import dev.based.vampyrix.api.event.ClientEvent;
import net.minecraft.entity.EntityLivingBase;

public class LivingUpdateEvent extends ClientEvent {
    private static final LivingUpdateEvent INSTANCE = new LivingUpdateEvent();

    private EntityLivingBase entity;

    public EntityLivingBase getEntity() {
        return this.entity;
    }

    public static LivingUpdateEvent get(EntityLivingBase entity) {
        INSTANCE.entity = entity;

        return INSTANCE;
    }
}
