/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XFile {

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
    public static void delete(String path){
        java.io.File file = new java.io.File(path);
        file.delete();
    }

    /**
     * Copy a file
     */
    public static void copy(String fromLocation, String toLocation) throws IOException {
        // create a new file
        create(toLocation);

        // read content from the old file and write to the new one
        FileInputStream inStream = new FileInputStream(fromLocation);
        FileOutputStream outStream = new FileOutputStream(toLocation);

        int offset = 0;
        int len = 1024;
        byte[] buffer = new byte[len];
        int numByteRead = 0;

        while((numByteRead = inStream.read(buffer, offset, len)) != -1) {
            outStream.write(buffer, offset, numByteRead);
        }

        inStream.close();
        outStream.close();
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
     * @ref http://tinyurl.com/executefile
     */
    public static void execute(String filePath) {
        try {
            Runtime runner = Runtime.getRuntime();
            runner.exec("cmd /c start " + filePath);
        } catch (IOException ex) {
            Logger.getLogger(XFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get file's content (for viewing file purpose)
     */
    public static String getContent(String filePath) throws IOException{
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try {
            String line = null;

            while((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
        } catch(Exception ex) {
            // ignore :)
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

    /**
     * Zip a file
     */
    public static void zip(String filePath) throws IOException {
        // TODO: zip file
    }

    /**
     * Unzip a file
     */
    public static void unzip(String filePath) throws IOException {
        // TODO: unzip file
    }
}
