/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.File;

/**
 * Path utils
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class PathHelper {
    /**
     * Is it a file?
     */
    public static Boolean isFile(String path) {
        File file = new File(path);
        return file.isFile();
    }

    /**
     * Is it a folder?
     */
    public static Boolean isFolder(String path) {
        File folder = new File(path);
        return folder.isDirectory();
    }

    /**
     * Get parent path
     */
    public static String geParentPath(String path) {
        return path.substring(0, path.lastIndexOf("\\\\"));
    }

    /**
     * Get parent name
     */
    public static String getParentName(String path)
    {
        int i = path.lastIndexOf("\\\\");
        if(i<0)
            return path;
        else
            return path.substring(i + 2, path.length()-1);
    }

    /**
     * Get file name from path
     */
    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("\\") + 1, path.length());
    }

    /**
     * Get filename without extension
     */
    public static String getFileNameWithoutExt(String path) {
        String filename = getFileName(path);
        return filename.substring(0, filename.lastIndexOf("."));
    }
}
