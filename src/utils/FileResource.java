/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import components.TextImageObj;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;
import sun.awt.shell.ShellFolder;


/**
 * File util
 * @author pmchanh
 */
public class FileResource {

    // temp dir
    private static File SYSTEM_TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    /**
     * List all files
     */
    public static Vector listFiles(String pathname, Boolean isSmallIcon) {
        File f = new File(pathname);
        if(f != null){
            File[] files = f.listFiles();
            int n = files.length;
            ArrayList<Object[]> dirArr = new ArrayList<Object[]>();
            ArrayList<Object[]> firArr = new ArrayList<Object[]>();
            for(int i = 0; i < n; i++ ) {
                Object[] item = new Object[4];
                ShellFolder sf;
                Icon icon = null;
                if(isSmallIcon){
                    FileSystemView view = FileSystemView.getFileSystemView();
                    icon = view.getSystemIcon(files[i]);
                }
                else{
                    try {
                       sf = ShellFolder.getShellFolder(files[i]);
                       icon = new ImageIcon(sf.getIcon(true));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(FileResource.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                String ext =  getExtension(files[i]);
                item[0] = new TextImageObj(getName(files[i]), icon,ext);
                item[1] = ext;
                item[2] = getSize(files[i]);
                item[3] = getDate(files[i]);
                if(files[i].isDirectory())
                    dirArr.add(item);
                else
                    firArr.add(item);
            }

            Collections.sort(dirArr, new Comparer());
            Collections.sort(firArr, new Comparer());
            Iterator iterDir = dirArr.iterator();
            Iterator iterFile = firArr.iterator();

            Vector rs = new Vector();
            if(f.getParent()!= null) {
                Object[] emptyRow = {TextImageObj.createEmptyObj(),"","","",""};
                rs.add(emptyRow);
            }

            while(iterDir.hasNext()) {
                rs.add(iterDir.next());
            }
                
            while(iterFile.hasNext()) {
                rs.add(iterFile.next());
            }
                
            return rs;
        }
        return null;

    }

    /**
     * Create a temp directory
     */
    public static String createTempDir() {
		File dir = new File(SYSTEM_TMP_DIR, "abc" + new Date().getTime() + (Math.ceil(10000f * Math.random())));
		dir.mkdirs();

		return dir.getPath() + "\\";
	}

    /**
     * An item is a file?
     */
    public static Boolean isFile(String path) {
        return new File(path).isFile();
    }

    /**
     * An item is a folder?
     */
    public static Boolean isFolder(String path) {
        return new File(path).isDirectory();
    }

    //<editor-fold defaultstate="collapsed" desc="Housekeeping ...">

    private static String getName(File f) {
        String name = f.getName();
        if(!f.isDirectory())
        {
            int e = name.lastIndexOf(".");
            if(e > 0)
                name = name.substring(0,e);
        }
        return name;
    }
    
    private static String getExtension(File f) {
        String extension = "";
        String name = f.getName();
        if(!f.isDirectory())
        {
            int e = name.lastIndexOf(".");
            if(e > 0)
                extension = name.substring(e + 1, name.length());
        }
        return extension;
    }

    private static String getSize(File f) {
        String rs = "<DIR>";
        if(!f.isDirectory())
            rs = "" + f.length();
        return rs;
    }

    private static String getDate(File f) {
        long d = f.lastModified();
        Date datetime = new Date(d);
        TimeZone zone = TimeZone.getTimeZone("GMT+07:00");
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(zone);
        cal.setTime(datetime);
        return getDate(cal);
    }

    private static String getDate(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH)+1;
	    int date = cal.get(Calendar.DAY_OF_MONTH);
	    int time = cal.get(Calendar.HOUR_OF_DAY);
	    int minute = cal.get(Calendar.MINUTE);
	    int second = cal.get(Calendar.SECOND);

        String kq = "";
        kq += date < 10 ? "0" + date :"" + date;
        kq += "/";
	    kq += month < 10 ? "0" + month:"" + month;
	    kq += "/" + year + " ";

	    kq += time < 10   ? "0" + time  +":" :"" + time + ":";
	    kq += minute < 10 ? "0" + minute+":" :"" +minute+ ":";
	    kq += second < 10 ? "0" + second :"" +second;

	    return kq;
    }

    //</editor-fold>
}
