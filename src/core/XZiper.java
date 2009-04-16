/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.*;
import utils.PathHelper;

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

    // TODO: Cường - append zip file

    // TODO: Cường - explore zip file

    public static void appendZip(String appendFiles, String sourceZip) throws IOException {

        File zip = new File(sourceZip);
        File fileAppend = new File(appendFiles);

        ArrayList<File> files = new ArrayList<File>();
        if(PathHelper.isFile(appendFiles)) {
            files.add(fileAppend);
        } else {
            listFiles(files, fileAppend);
        }

        FileInputStream inStream = null;
        byte[] data = new byte[BUFFER];
        ZipFile zipFile = new ZipFile(zip);
        Enumeration entries = zipFile.entries();

        ZipOutputStream outStream = new ZipOutputStream(new FileOutputStream(sourceZip));

        while(entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            InputStream is = zipFile.getInputStream(entry);
            outStream.putNextEntry(entry);

            int count = 0;
            while ((count = is.read(data, 0, BUFFER)) >0)  {
                outStream.write(data, 0, count);
            }
            outStream.closeEntry();
            is.close();
        }

        for(int i = 0; i < files.size(); ++i) {
            File file = files.get(i);
            inStream = new FileInputStream(file);
            outStream.putNextEntry(new ZipEntry(file.getPath()));
            int count = 0;
            while ((count = inStream.read(data, 0, BUFFER)) != -1)  {
                outStream.write(data, 0, count);
            }
            outStream.closeEntry();
            inStream.close();
        }
        outStream.flush();
        outStream.close();
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

    private static void listFiles(ArrayList<File> files, File folder) {
        for(File item: folder.listFiles()) {
            if(item.isFile()) {
                files.add(item);
            } else {
                listFiles(files, item);
            }
        }
    }
}
