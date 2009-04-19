/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import utils.PathHelper;

/**
 * Splitter - split and merge big file
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XSplitter {
    /**
     * Split big file to smaller files
     */
    public static void split(String srcPath, String destPath) throws IOException {
        File bigFile = new File(srcPath);

        int sizeOfEachFile = 1024000;   // 1 MB
        int numberOfFiles = (int) (Math.ceil((double)bigFile.length() / sizeOfEachFile));
        String filename = PathHelper.getFileName(srcPath);

        InputStream inStream = new FileInputStream(srcPath);
        for(int i = 0; i < numberOfFiles; ++i) {
            String destFile = destPath + filename + "." + reformatNumber(i);
            OutputStream outStream = new FileOutputStream(destFile);
            int numRead = 0;
            byte[] buffer = new byte[sizeOfEachFile];

            if((numRead = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, numRead);
            }
            outStream.close();
        }
        inStream.close();       
    }

    /**
     * Merge files
     */
    public static void merge(ArrayList<String> srcFiles, String destFolder) throws IOException {
        // build the destination path
        String filePath = srcFiles.get(0).substring(0, srcFiles.get(0).length() - 4);
        String fileName = PathHelper.getFileName(filePath);
        String destPath = destFolder + fileName;
        
        InputStream inStream = null;
        OutputStream outStream = new FileOutputStream(destPath);

        for(String file : srcFiles) {
            int numRead = 0;
            byte[] buffer = new byte[1024];
            inStream = new FileInputStream(file);

            while((numRead = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, numRead);
            }
            inStream.close();
        }
        outStream.close();
    }

    private static String reformatNumber(int number) {
        if(number < 10) {
            return "00" + number;
        } else if(number < 100) {
            return "0" + number;
        }
        return "" + number;
    }
}
