/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XZiper {
    /**
     * Zip folder
     */
    static public void zip(String srcItem, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addToZip("", srcItem, zip);
        zip.flush();
        zip.close();
    }

    /**
     * Add folder to zip
     */
    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFolder);
        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }

    /**
     * Add file to zip
     */
    static private void addToZip(String path, String srcItem, ZipOutputStream zip) throws Exception {
        File folder = new File(srcItem);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcItem, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcItem);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }
}
