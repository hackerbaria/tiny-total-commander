/*
 * To change this template, choose Tools | Templates
 * and open the template inStream the editor.
 */

package core;

import java.io.*;

/**
 * Item (file + folder)
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XItem {
    // buffer size
    private static final int BUFFER = 1024;

    /**
     * Create a new file
     */
    public static void createFile(String filePath) throws IOException {
        new File(filePath).createNewFile();
    }

    /**
     * Create a new folder
     */
    public static void createFolder(String folderPath) throws IOException {
        File folder = new File(folderPath);

        // create item
        if(folderPath.contains("\\")) {   // item and sub folders
            folder.mkdirs();
        } else {                    // single item
            folder.mkdir();
        }
    }

    /**
     * Rename an item
     */
    public static void rename(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        if(!oldFile.renameTo(newFile)) {
            throw new IOException("Cannot rename the item.");
        }
    }

    /**
     * Delete an item
     */
    public static void delete(String itemPath) throws IOException {
        java.io.File item = new java.io.File(itemPath);
        if(item.isFile()) {
            // delete folder
            item.delete();
        } else {
            // delete folder
            deleteFiles(item);  // delete all sub items
            item.delete();      // delete item itself
        }
    }

    private static void deleteFiles(java.io.File folder) {
        for (java.io.File item : folder.listFiles()) {
            if(item.isDirectory()) {
                // item is a non-empty item
                deleteFiles(item);
            } 
            // item is a folder or an empty item
            item.delete();
        }
    }

    /**
     * Copy an item
     */
    public static void copy(String srcItemPath, String destItemPath) throws IOException {
        File srcItem = new File(srcItemPath);
        File destItem = new File(destItemPath);
        
        if(srcItem.isFile()) {
            // copy file
            copyFile(srcItem, destItem);
        } else {
            // copy folder
            copyFolder(srcItem, destItem);
        }
    }

    private static void copyFile(File srcFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        InputStream inStream = new FileInputStream(srcFile);
        OutputStream outStream = new FileOutputStream(destFile);

        byte[] buf = new byte[BUFFER];
        int len;
        while ((len = inStream.read(buf)) > 0) {
            outStream.write(buf, 0, len);
        }
        inStream.close();
        outStream.close();
    }

    private static void copyFolder(File sourceDir, File destDir) throws IOException {
        if(!destDir.exists()) {
            destDir.mkdir();
        }

        File[] children = sourceDir.listFiles();
        for(File sourceChild : children) {
            File destChild = new File(destDir, sourceChild.getName());
            if(sourceChild.isDirectory()) {
                // sub folders => recursion
                copyFolder(sourceChild, destChild);
            } else {
                // folder => copy
                copyFile(sourceChild, destChild);
            }
        }
    }

    /**
     * Move an item
     */
    public static void move(String oldPath, String newPath) throws IOException{
        // make a copy then delete the old item
        copy(oldPath, newPath);
        delete(oldPath);
    }

        /**
     * Execute a file
     * ref: http://frank.neatstep.com/node/84
     */
    public static void executeFile(String filePath) throws IOException {
        // sample: cmd /c "start c:\My" "Documents\Some" "File.txt"
        filePath = filePath.replace(" ", "\" \"");
        Runtime.getRuntime().exec("cmd /c \"start " + filePath + "\"");
    }

    /**
     * Get file's content (for viewing file)
     */
    public static String getFileContent(String filePath) throws IOException{
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line = null;
        while((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }

        return builder.toString();
    }
}
