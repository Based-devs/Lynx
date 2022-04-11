package dev.based.vampyrix.impl.Commands;

import dev.based.vampyrix.api.command.command;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import static dev.based.vampyrix.api.util.Wrapper.mc;

public class CommandCoords extends command {
    public CommandCoords(){
        super("cords","Copy your cords to the clipboard","cords");
    }

    @Override
    public void onCommand(String[] args){
        if (mc.player == null ){
            return;
        }

        double xcords = mc.player.lastTickPosX;
        double ycords = mc.player.lastTickPosY;
        double zcords =  mc.player.lastTickPosZ;

        String cord = "x: "+ String.format("%.2f", xcords)+" y: "+String.format("%.2f", ycords)+" z: "+String.format("%.2f", zcords);
        StringSelection stringSelection = new StringSelection(cord);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

    }

}
