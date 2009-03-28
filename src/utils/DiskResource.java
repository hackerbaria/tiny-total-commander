/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
/**
 *
 * @author pmchanh
 */
public class DiskResource
{
    public static String[] getAll()
    {
        File[] fs = File.listRoots();
        String[] rs = new String[fs.length];
        for(int i = 0; i < fs.length; i++)
        {
            String item = refinePath(fs[i]).toLowerCase();
            rs[i] = item;
        }
        return rs;
    }
    public static String refinePath(File f)
    {
        String rs = "";
        String path = f.getPath().replace(":\\", "");
        rs = "[-" + path + "-] " ;
        return rs;
    }

    public static String getInfo(String pathName)
    {
        FileSystemView view = FileSystemView.getFileSystemView();
        File f = new File(pathName);
        String label = view.getSystemDisplayName(f);        
        if(label == null)
            return "[infinitive]";
        label = label.trim();
        if(label == null || label.length() < 1)
            return "[infinitive]";
        int u = label.indexOf("(");
        label = label.substring(0,u-1);
        String kq = "";
        kq ="[" + label + "] " + f.getFreeSpace()/1024 + " k "
                + " of " + f.getTotalSpace()/1024 + "k free";
        return kq;
    }
    
}
