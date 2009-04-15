/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.*;
import java.util.zip.*;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XZiper {
    /**
     * Buffer size
     */
    private static final int BUFFER = 1024;

    /**
     * Zip file + folder
     */
    public static void zip(String srcItem, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addToZip("", srcItem, zip);
        zip.flush();
        zip.close();
    }

    /**
     * Unzip file + folder
     */
    public static void unzip(String srcZipFile, String destItem) throws Exception {
        File inFile = new File(srcZipFile);
        File outFolder = new File(destItem);

        BufferedOutputStream out = null;
        ZipInputStream  in = new ZipInputStream(
                                     new BufferedInputStream(
                                          new FileInputStream(inFile)));
        ZipEntry entry;
        while((entry = in.getNextEntry()) != null)
        {
            int count;
            byte data[] = new byte[BUFFER];

            String fullpath = outFolder.getPath() + "\\" + entry.getName();
            String dirs = fullpath.substring(0, fullpath.lastIndexOf("\\"));
            
            File file = new File(dirs);
            if(!file.exists()) {
                file.mkdirs();
            }

            // write the files to the disk
            out = new BufferedOutputStream(
                      new FileOutputStream(outFolder.getPath() + "\\" + entry.getName()),BUFFER);

            while ((count = in.read(data)) > 0) {
                 out.write(data, 0, count);
            }
        }
        in.close();
        out.close();
    }

    /**
     * Add folder to zip
     */
    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFolder);
        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addToZip(folder.getName(), srcFolder + "\\" + fileName, zip);
            } else {
                addToZip(path + "\\" + folder.getName(), srcFolder + "\\" + fileName, zip);
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
            zip.putNextEntry(new ZipEntry(path + "\\" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }
}
