/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.File;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class FileHelper {
    public static Boolean isFile(String path) {
        File file = new File(path);
        return file.isFile();
    }

    public static Boolean isFolder(String path) {
        File folder = new File(path);
        return folder.isDirectory();
    }
}
