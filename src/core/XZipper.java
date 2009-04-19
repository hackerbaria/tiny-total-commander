/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.*;
import utils.FileResource;

/**
 * Zipper - zip, unzip file
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 * ref http://www.java2s.com/Code/Java/File-Input-Output/UseJavacodetozipafolder.htm
 */
public class XZipper {

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

        if(FileResource.isFile(srcItem)) {
            addFileToZip("", srcItem, zip);
        } else {
            addFolderToZip("", srcItem, zip);
        }

        zip.flush();
        zip.close();    
	}

    /**
     * Unzip file + folder
     */
    public static void unzip(String srcZipFile, String destItem) throws Exception {

        File inFile = new File(srcZipFile);
        File outFolder = new File(destItem);

        ZipInputStream  in = new ZipInputStream( new FileInputStream(inFile));
        OutputStream out = null;
        
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
            out = new FileOutputStream(fullpath);
            while ((count = in.read(data)) > 0) {
                 out.write(data, 0, count);
            }
        }
        in.close();
        out.close();
    }

    /**
     * Append file/folder to zip file
     */
    public static void appendZip(String srcItem, String destZipFile) throws Exception {        

        File zip = new File(destZipFile);
        File fileAppend = new File(srcItem);

        // files will be appended to the zip file
        ArrayList<File> files = new ArrayList<File>();
        if(FileResource.isFile(srcItem)) {
            files.add(fileAppend);
        } else {
            listFiles(files, fileAppend);
        }

        FileInputStream inStream = null;
        byte[] data = new byte[BUFFER];
        ZipFile zipFile = new ZipFile(zip);
        Enumeration entries = zipFile.entries();

        // create a temp zip file
        String tempPath = System.getProperty("java.io.tmpdir") + zip.getName();
        ZipOutputStream outStream = new ZipOutputStream(new FileOutputStream(tempPath));

        while(entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            InputStream is = zipFile.getInputStream(entry);
            outStream.putNextEntry(entry);

            int count = 0;
            while ((count = is.read(data)) >0)  {
                outStream.write(data, 0, count);
            }
            outStream.closeEntry();
            is.close();
        }

        for(int i = 0; i < files.size(); ++i) {
            File file = files.get(i);
            inStream = new FileInputStream(file);
            outStream.putNextEntry(new ZipEntry(file.getName()));
            int count = 0;
            while ((count = inStream.read(data)) != -1)  {
                outStream.write(data, 0, count);
            }
            outStream.closeEntry();
            inStream.close();
        }
        outStream.flush();
        outStream.close();

        // delete the temp zip file then copy to the right location
        zip.delete();
        XItem.copy(tempPath, destZipFile);
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

    private static void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "\\" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFolder);
        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "\\" + fileName, zip);
            } else {
                addFileToZip(path + "\\" + folder.getName(), srcFolder + "\\" + fileName, zip);
            }
        }
    }
}
