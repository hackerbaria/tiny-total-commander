/*
 * To change this template, choose Tools | Templates
 * and open the template inStream the editor.
 */

package core;

import java.io.*;

/**
 * Folder
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XFolder {
    // buffer size
    private static final int BUFFER = 1024;

    /**
     * Create a new item
     */
    public static void create(String path) throws IOException {
        java.io.File file = new java.io.File(path);

        // create item
        if(path.contains("\\")) {   // item and sub folders
            file.mkdirs();
        } else {                    // single item
            file.mkdir();
        }
    }

     /**
     * Rename a item
     */
    public static void rename(String oldPath, String newPath) throws IOException {
        XFile.rename(oldPath, newPath);
    }

    /**
     * Delete a item
     * @param itemPath
     */
    public static void delete(String itemPath) throws IOException {
        java.io.File item = new java.io.File(itemPath);
        if(item.isFile()) {
            // delete file
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
            // item is a file or an empty item
            item.delete();
        }
    }

    /**
     * Copy a item
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
                // file => copy
                copyFile(sourceChild, destChild);
            }
        }
    }

    /**
     * Move a item
     */
    public static void move(String oldPath, String newPath) throws IOException{
        // make a copy then delete the old item
        copy(oldPath, newPath);
        delete(oldPath);
    }
}
