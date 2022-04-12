package dev.based.vampyrix;

import dev.based.vampyrix.api.command.CommandManager;
import dev.based.vampyrix.api.event.EventManager;
import dev.based.vampyrix.api.module.ModuleManager;
import dev.based.vampyrix.impl.clickgui.ClickGUIScreen;
import me.bush.eventbus.bus.EventBus;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid = Vampyrix.MODID, name = Vampyrix.NAME, version = Vampyrix.VERSION)
public class Vampyrix {
    public static final String MODID = "vampyrix", NAME = "Vampyrix", VERSION = "0.1";

    @Mod.Instance
    public static Vampyrix INSTANCE;

    private final Logger logger = LogManager.getLogger(MODID);
    private final EventBus eventBus = new EventBus();

    private ModuleManager moduleManager;
    private EventManager eventManager;
    private CommandManager commandManager;

    private ClickGUIScreen clickGUIScreen;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(INSTANCE);

        this.logger.info("Setting up Vampyrix {}...", Vampyrix.VERSION);

        this.moduleManager = new ModuleManager();
        this.logger.info("Modules Initialized.");

        this.eventManager = new EventManager();
        this.logger.info("Events Initialized.");

        this.commandManager = new CommandManager();
        this.logger.info("Commands Initialized.");

        this.clickGUIScreen = new ClickGUIScreen();
        this.logger.info("ClickGui initialized.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
         Display.setTitle(Vampyrix.NAME + " " + Vampyrix.VERSION);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public ClickGUIScreen getClickGUIScreen() {
        return this.clickGUIScreen;
    }
}
