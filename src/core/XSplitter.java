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
import utils.PathHelper;

/**
 *
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
        String filename = PathHelper.getFileNameWithoutExt(srcPath);
        String ext = ".ttc";

        InputStream inStream = new FileInputStream(srcPath);
        for(int i = 0; i < numberOfFiles; ++i) {
            String destFile = destPath + filename + ext + "." + i;
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
}
