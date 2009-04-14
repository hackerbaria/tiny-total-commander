/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.*;

/**
 * Folder
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XFolder {
    /**
     * Create a new folder
     */
    public static void create(String path) throws IOException {
        java.io.File file = new java.io.File(path);

        // create folder
        if(path.contains("\\")) {   // folder and sub folders
            file.mkdirs();
        } else {                    // single folder
            file.mkdir();
        }
    }

     /**
     * Rename a folder
     */
    public static void rename(String oldName, String newName) throws IOException {
        java.io.File file = new java.io.File(oldName);

        // get partial path (parent folder)
        String partialPath = oldName.substring(0, oldName.lastIndexOf("/"));

        // rename
        file.renameTo(new java.io.File(partialPath + newName));
    }

    /**
     * Delete a folder
     * @param folderPath
     */
    public static void delete(String folderPath) throws IOException {
        java.io.File folder = new java.io.File(folderPath);

        if(folder.exists()) {
            deleteFiles(folder);  // delete all sub items
            folder.delete();      // delete folder itself
        }
    }

    private static void deleteFiles(java.io.File folder) {
        for (java.io.File item : folder.listFiles()) {
            if(item.isDirectory()) {
                // item is folder
                deleteFiles(item);
            } else {
                // item is file
                item.delete();
            }
        }
    }

    /**
     * Copy a folder
     */
    public static void copy(String sourceDir, String destDir) throws IOException {
        copyInternal(new File(sourceDir), new File(destDir));
    }

    private static void copyInternal(File sourceDir, File destDir) throws IOException {
        // create folder if not exist
        if(!destDir.exists()) {
            destDir.mkdir();
        }

        File[] children = sourceDir.listFiles();
        for(File sourceChild : children) {
            File destChild = new File(destDir, sourceChild.getName());
            if(sourceChild.isDirectory()) {
                // sub folders => recursion
                copyInternal(sourceChild, destChild);
            } else {
                // file => copy
                XFile.copy(sourceChild.getAbsolutePath(), destChild.getAbsolutePath());
            }
        }
    }

    /**
     * Move a folder
     */
    public static void move(String oldPath, String newPath) throws IOException{
        // make a copy then delete the old folder
        copy(oldPath, newPath);
        delete(oldPath);
    }

    /**
     * Zip a folder
     */
    public static void zip(String folderPath) throws IOException {
        // TODO: zip folder
    }

    /**
     * Unzip a folder
     */
    public static void unzip(String folderPath) throws IOException {
        // TODO: unzip folder
    }

}
