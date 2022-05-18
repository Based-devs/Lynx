package com.based.lynx.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileUtil {
    public static void saveFile(File file, ArrayList<String> content) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        for (String s : content) {
            out.write(s);
            out.write("\r\n");
        }
        out.close();
    }

    public static ArrayList<String> loadFile(File file) throws IOException {
        String line;
        ArrayList<String> content = new ArrayList<String>();
        FileInputStream stream = new FileInputStream(file.getAbsolutePath());
        DataInputStream in = new DataInputStream(stream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while ((line = br.readLine()) != null) {
            content.add(line);
        }
        br.close();
        return content;
    }
}
