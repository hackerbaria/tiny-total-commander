/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.awt.Desktop;
import java.io.*;

/**
 * File
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XFile {

    /**
     * Buffer size
     */
    private static final int BUFFER = 1024;
    
    /**
     * Create a new file
     */
    public static void create(String path) throws IOException {
        java.io.File file = new java.io.File(path);
        file.createNewFile();
    }

    /**
     * Delete a file
     */
    public static void delete(String path) throws IOException {
        java.io.File file = new java.io.File(path);
        file.delete();
    }

    /**
     * Copy a file
     */
    public static void copy(String source, String dest) throws IOException {

        File srcFile = new File(source);
        File destFile = new File(dest);

        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        InputStream in = new FileInputStream(srcFile);
        OutputStream out = new FileOutputStream(destFile);

        // Copy the bits from instream to outstream
        byte[] buf = new byte[BUFFER];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    /**
     * Move a file
     */
    public static void move(String oldPath, String newPath) throws IOException {
        copy(oldPath, newPath);
        delete(oldPath);
    }

    /**
     * Execute a file
     * ref: http://frank.neatstep.com/node/84
     */
    public static void execute(String filePath) throws IOException {
        // sample: cmd /c "start c:\My" "Documents\Some" "File.txt"
        filePath = filePath.replace(" ", "\" \"");
        Runtime.getRuntime().exec("cmd /c \"start " + filePath + "\"");
    }

    /**
     * Get file's content (for viewing file)
     */
    public static String getContent(String filePath) throws IOException{
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = null;
        while((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }

        return builder.toString();
    }

    /**
     * Rename a file
     */
    public static void rename(String oldPath, String newPath) throws IOException {
        java.io.File file = new java.io.File(oldPath);
        file.renameTo(new java.io.File(newPath));
    }
}
