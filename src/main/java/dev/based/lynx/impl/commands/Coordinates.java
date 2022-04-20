package dev.based.lynx.impl.commands;

import dev.based.lynx.api.command.Command;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import static dev.based.lynx.api.util.Wrapper.mc;

public class Coordinates extends Command {
    public Coordinates() {
        super("coordinates", "coordinates", "coordinates", "coords", "coordinates");
    }

    @Override
    public void execute(String[] args) {
        if (mc.player == null) {
            return;
        }

        double xcoords = mc.player.lastTickPosX;
        double ycoords = mc.player.lastTickPosY;
        double zcoords = mc.player.lastTickPosZ;

        String cord = "x: " + String.format("%.2f", xcoords) + " y: " + String.format("%.2f", ycoords) + " z: " + String.format("%.2f", zcoords);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(cord), null);
    }
}
