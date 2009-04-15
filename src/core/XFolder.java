/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Folder
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XFolder {
    /**
     * Buffer size
     */
    private static final int BUFFER = 1024;

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
                // item is a non-empty folder
                deleteFiles(item);
            } 
            // item is a file or an empty folder
            item.delete();
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
    public static void zip(String inFolderPath, String outFilePath) throws IOException {
        // TODO: zip folder
        zipInternal(new File(inFolderPath), new File(outFilePath));
    }

    private static void zipInternal(File inFolder, File outFile) throws IOException {
        BufferedInputStream inStream = null;
        ZipOutputStream outStream = new ZipOutputStream(
                                        new BufferedOutputStream(
                                              new FileOutputStream(outFile)));

        byte[] data = new byte[BUFFER];
        String[] files = inFolder.list();

        for(int i = 0; i < files.length; ++i) {
            inStream = new BufferedInputStream(new FileInputStream(inFolder.getPath() + "/" + files[i]), BUFFER);
            outStream.putNextEntry(new ZipEntry(files[i]));
            int count = 0;
            while((count = inStream.read(data)) > 0) {
                outStream.write(data, 0, count);
            }
        }

        inStream.close();
        outStream.close();
    }

    /**
     * Unzip a folder
     */
    public static void unzip(String folderPath) throws IOException {
        // TODO: unzip folder
    }

}
