/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.*;
import utils.Konstant;
import utils.PathHelper;

/**
 * Ziper
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XZiper {
    /**
     * Zip file + folder
     */
    public static void zip(String srcItem, String destZipFile) throws Exception {
        File item = new File(srcItem);
        File zip = new File(destZipFile);

        // files will be appended to the zip file
        ArrayList<File> files = new ArrayList<File>();
        if(PathHelper.isFile(srcItem)) {
            files.add(item);
        } else {
            listFiles(files, item);
        }

        // TODO: Cường - bug zip file
        // Mô tả: các folder rỗng bị bỏ qua, ko có trong file zip

        FileInputStream inStream = null;
        ZipOutputStream outStream = new ZipOutputStream(new FileOutputStream(zip.getPath()));
        byte[] data = new byte[Konstant.BUFFER];

        for(int i = 0; i < files.size(); ++i) {
            File file = files.get(i);
            inStream = new FileInputStream(file);
            outStream.putNextEntry(new ZipEntry(file.getPath()));
            int count = 0;
            while ((count = inStream.read(data)) > 0) {
                 outStream.write(data, 0, count);
            }

            outStream.closeEntry();
            inStream.close();
        }

        outStream.flush();
        outStream.close();
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
            byte data[] = new byte[Konstant.BUFFER];

            String relativepath = entry.getName().substring(3, entry.getName().length());
            String fullpath = outFolder.getPath() + "\\" + relativepath;
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

    // TODO: Cường - explore zip file

    /**
     * Append file/folder to zip file
     */
    public static void appendZip(String appendFiles, String sourceZip) throws IOException {
        File zip = new File(sourceZip);
        File fileAppend = new File(appendFiles);

        // files will be appended to the zip file
        ArrayList<File> files = new ArrayList<File>();
        if(PathHelper.isFile(appendFiles)) {
            files.add(fileAppend);
        } else {
            listFiles(files, fileAppend);
        }

        FileInputStream inStream = null;
        byte[] data = new byte[Konstant.BUFFER];
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
            outStream.putNextEntry(new ZipEntry(file.getPath()));
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
        XFile.copy(tempPath, sourceZip);
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
