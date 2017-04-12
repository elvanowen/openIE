package id.ac.itb.openie.utils;

import com.github.slugify.Slugify;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by elvanowen on 2/22/17.
 */
public class Utilities {
    public static void writeToFile(String _directoryPath, String fileName, String content) {

        fileName = fileName.replaceAll("(\\.txt$)","");

        // Make sure filename is valid
        Slugify slg = new Slugify();
        fileName = slg.slugify(fileName);

        File directoryPath = new File(_directoryPath);

        // Create directory if not exist
        if (!directoryPath.exists()) {
            try{
                directoryPath.mkdir();
            } catch(SecurityException e){
                System.err.println(e);
            }
        }

        // Write to file
        try{
            PrintWriter writer = new PrintWriter(new File(directoryPath, fileName + ".txt"), "UTF-8");
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static Boolean removeFile(File fileToBeRemoved) {
        return fileToBeRemoved.delete();
    }

    public static ArrayList<File> getDirectoryFiles(final File folder) {

        ArrayList<File> files = new ArrayList<File>();

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                files.addAll(getDirectoryFiles(fileEntry));
            } else if (fileEntry.isFile() && !fileEntry.isHidden()) {
                files.add(new File(folder.getAbsolutePath(), fileEntry.getName()));
            }
        }

        return files;
    }

    public static String getFileContent(File file) {
        BufferedReader br = null;
        FileReader fr = null;
        String content = "";

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                content += sCurrentLine + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (fr != null) fr.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return content;
    }
}
