/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.*;

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
    public static void delete(String path) throws IOException {
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
    public static void execute(String filePath) throws IOException {
        Runtime runner = Runtime.getRuntime();
        runner.exec("cmd /c start " + filePath);
    }

    /**
     * Get file's content (for viewing file purpose)
     */
    public static String getContent(String filePath) throws IOException{
        StringBuilder builder = new StringBuilder();

        FileInputStream stream = new FileInputStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        int len = 1024;
        int offset = 0;
        char[] buffer = new char[len];
        int numCharRead = 0;

        // TODO: buffer (trash content)
        
        do{
            numCharRead = reader.read(buffer, offset, len);
            builder.append(buffer);
        }while(numCharRead >= len);

        return builder.toString();
    }

    /**
     * Rename a file
     */
    public static void rename(String oldName, String newName) {
        java.io.File file = new java.io.File(oldName);

        // get partial path (parent folder)
        String partialPath = oldName.substring(0, oldName.lastIndexOf("/"));

        // rename
        file.renameTo(new java.io.File(partialPath + newName));
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
