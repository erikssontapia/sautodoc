package net.bluetab.sautodoc.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> readFileLineByLine(String pathName){
        BufferedReader reader;
        List<String> fileLines = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(pathName));
            String line = reader.readLine();
            fileLines.add(line);
            while (line != null) {
                line = reader.readLine();
                fileLines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLines;
    }

    public static boolean createPath(String path){
        File file = new File(path);
        return file.mkdirs();
    }

    public static void writeFileLineByLine(String pathname, List<String> fileLines) {
        File fout = new File(pathname);
        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (String line: fileLines) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
