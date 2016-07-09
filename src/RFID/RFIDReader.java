package RFID;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

public class RFIDReader {
    String path = null;
    public RFIDReader(){
        init();
        if (path == null){
            JOptionPane.showMessageDialog(null,"You must select a RFID document");
            return;
        }
    }

    public void init(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setAcceptAllFileFilterUsed(false);

        // 显示所有文件
        fileChooser.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File file) {
                String name = file.getName();
                if (name.endsWith("txt"))
                    return true;
                else
                    return false;
            }
            public String getDescription() {
                return "记事本文件(*.txt)";
            }
        });

        fileChooser.showDialog(null, null);
        path = fileChooser.getSelectedFile().getPath();
    }

    public static void main(String[] args){
        RFIDReader reader = new RFIDReader();
        System.out.println(reader.getRandomRFID());
    }

    public String getRandomRFID(){
        Stream<String> lines = null;
        try {
            lines = Files.lines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return lines.skip((long)(Math.random()*10-1)).findFirst().get();
    }
}
