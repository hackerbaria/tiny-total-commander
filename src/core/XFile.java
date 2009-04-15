/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * File
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XFile {

    /**
     * Buffer used in reading file
     */
    private static final int BUFFER = 1024;

    /**
     * Create a new file
     */
    public static void create(String path) throws IOException {
        java.io.File file = new java.io.File(path);
        file.createNewFile();
    }

    /**
     * Delete a file
     */
    public static void delete(String path) throws IOException {
        java.io.File file = new java.io.File(path);
        file.delete();
    }

    /**
     * Copy a file
     */
    public static void copy(String source, String dest) throws IOException {
        copyInternal(new File(source), new File(dest));
    }

    private static void copyInternal(File source, File dest) throws IOException {
        if(!dest.exists()) {
            dest.createNewFile();
        }

        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(dest);

        // Copy the bits from instream to outstream
        byte[] buf = new byte[BUFFER];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    /**
     * Move a file
     */
    public static void move(String oldPath, String newPath) throws IOException {
        copy(oldPath, newPath);
        delete(oldPath);
    }

    /**
     * Execute a file
     * @ref http://tinyurl.com/executefile
     */
    public static void execute(String filePath) throws IOException {
        Runtime.getRuntime().exec("cmd /c start " + filePath);
    }

    /**
     * Get file's content (for viewing file)
     */
    public static String getContent(String filePath) throws IOException{
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = null;
        while((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }

        return builder.toString();
    }

    /**
     * Rename a file
     */
    public static void rename(String oldPath, String newPath) throws IOException {
        java.io.File file = new java.io.File(oldPath);
        file.renameTo(new java.io.File(newPath));
    }

    /**
     * Zip a file
     */
    public static void zip(String inFilePath, String outFilePath) throws IOException {
        zipInternal(new File(inFilePath), new File(outFilePath));
    }

     public static void zipInternal(File inFile, File outFile) throws IOException {
        InputStream inStream = new FileInputStream(inFile);
        ZipOutputStream outStream = new ZipOutputStream(new FileOutputStream(outFile));

        outStream.putNextEntry(new ZipEntry(inFile.getName()));
        byte[] buf = new byte[BUFFER];
        int len = 0;
        while((len = inStream.read(buf)) > 0) {
            outStream.write(buf, 0, len);
        }
        outStream.closeEntry();

        inStream.close();
        outStream.close();
    }

    /**
     * Unzip a file
     */
    public static void unzip(String inFilePath, String outFilePath) throws IOException {
        ZipInputStream inStream = new ZipInputStream(new FileInputStream(inFilePath));
        BufferedOutputStream outStream = null;

        ZipEntry entry = null;
        int count = 0;
        while((entry = inStream.getNextEntry()) != null) {
            byte buffer[] = new byte[BUFFER];
            outStream = new BufferedOutputStream(new FileOutputStream(outFilePath + "/" + entry.getName()), BUFFER);
            while((count = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, count);
            }
        }

        inStream.close();
        outStream.close();
    }
}
