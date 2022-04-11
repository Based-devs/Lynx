package dev.based.vampyrix.impl.commands;

import dev.based.vampyrix.api.command.Command;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

import static dev.based.vampyrix.api.util.Wrapper.mc;

public class CommandCoords extends Command {
    public CommandCoords(){
        super("Coordinates","Copies your coords to the clipboard.","coordinates", "coords", "coordinates");
    }

    @Override
    public void onCommand(String[] args){
        if (mc.player == null ){
            return;
        }

        double xcords = mc.player.lastTickPosX;
        double ycords = mc.player.lastTickPosY;
        double zcords = mc.player.lastTickPosZ;

        String cord = "x: " + String.format("%.2f", xcords) + " y: " + String.format("%.2f", ycords) + " z: " + String.format("%.2f", zcords);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(cord), null);
    }
}
