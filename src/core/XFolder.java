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
    public static void rename(String oldName, String newName) {
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
    public static void delete(String folderPath) {
        java.io.File folder = new java.io.File(folderPath);

        if(folder.exists()) {
            deleteFiles(folder);  // delete all sub items
            folder.delete();      // delete folder itself
        }
    }

    /**
     * Delete all files and sub folders in a folder
     * @param folder
     */
    private static void deleteFiles(java.io.File folder) {
        for (java.io.File item : folder.listFiles()) {
            // item is folder
            if(item.isDirectory()) {
                deleteFiles(item);
            }
            // item is file
            item.delete();
        }
    }

    /**
     * Copy a folder
     */
    public static void copy(String fromLocation, String toLocation) throws IOException {
        // TODO: copy folder

        // create folders
        // copy all files to new folder
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
