/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 * Path util
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class PathHelper {
    /**
     * Get parent path
     */
    public static String getParentPath(String path) {
        if(path.charAt(path.length() - 1) == '\\') {
            path = path.substring(0, path.length() - 1);
        }

        int index = path.lastIndexOf("\\");
        if(index < 0)
            return path;
        
        return path.substring(0, path.lastIndexOf("\\") + 1);
    }

    /**
     * Get parent name
     */
    public static String getParentName(String path) {
        if(path.charAt(path.length() - 1) == '\\') {
            path = path.substring(0, path.length() - 1);
        }
        int index = path.lastIndexOf("\\");
        if(index < 0)
            return path;
        
        return path.substring(path.lastIndexOf("\\") + 1, path.length());
    }

    /**
     * Get root (drive)
     */
    public static String getRoot(String path) {
        return path.substring(0, 3);
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
        if(filename.lastIndexOf(".") < 0) {
            return filename;
        }
        return filename.substring(0, filename.lastIndexOf("."));
    }
}
