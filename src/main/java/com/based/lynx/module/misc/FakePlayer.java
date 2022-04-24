package com.based.lynx.module.misc;

import com.based.lynx.module.Category;
import com.based.lynx.module.Module;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class FakePlayer extends Module {

    public FakePlayer() {
        super("FakePlayer", "Creates A Fake Player", Category.MISC);
    }

    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(FakePlayer.mc.world, new GameProfile(UUID.fromString("e4146954-57b4-46fd-ab97-4ab8d8034c31"), "FakePlayer"));
        fakePlayer.copyLocationAndAnglesFrom(FakePlayer.mc.player);
        FakePlayer.mc.world.addEntityToWorld(69420, fakePlayer);
    }

    @Override
    public void onDisable() {
        FakePlayer.mc.world.removeEntityFromWorld(69420);
    }
}