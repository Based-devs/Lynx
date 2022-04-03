package dev.based.vampyrix.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import dev.based.vampyrix.api.event.EventManager;
import dev.based.vampyrix.api.module.ModuleManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = Vampyrix.MODID, name = Vampyrix.NAME, version = Vampyrix.VERSION)
public class Vampyrix {

    public static final String MODID = "vampyrix";
    public static final String NAME = "vampyrix";
    public static final String VERSION = "0.1";

    @Instance
    public static Vampyrix INSTANCE;

    public static final Logger LOGGER = LogManager.getLogger("vampyrix");

    private ModuleManager moduleManager;
    private EventManager eventManager;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(INSTANCE);

        LOGGER.info("Setting up Vampyrix {}...", Vampyrix.VERSION);

        moduleManager = new ModuleManager();
        LOGGER.info("Modules Initialized.");

        eventManager = new EventManager();
        LOGGER.info("Events Initialized.");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Display.setTitle(Vampyrix.NAME + "Vampyrix 0.1" + Vampyrix.VERSION);
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

}
