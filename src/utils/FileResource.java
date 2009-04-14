/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import ExtendComponent.TextImageObj;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import sun.awt.shell.ShellFolder;


/**
 *
 * @author pmchanh
 */
public class FileResource {
    public static Vector listFiles(String pathname) {
        File f = new File(pathname);
        File[] files = f.listFiles();
        int n = files.length;        
        ArrayList<Object[]> dirArr = new ArrayList<Object[]>();
        ArrayList<Object[]> firArr = new ArrayList<Object[]>();
        for(int i = 0; i < n; i++ )
        {
            Object[] item = new Object[5];
            ShellFolder sf;
            Icon icon = null;
            try {

                // TODO: bug - Chánh
                // nếu folder có chứa file .svn thì khi bấm [...] bị die

                sf = ShellFolder.getShellFolder(files[i]);
                icon = new ImageIcon(sf.getIcon(true).getScaledInstance(19, 19, Image.SCALE_SMOOTH));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileResource.class.getName()).log(Level.SEVERE, null, ex);
            }

            item[0] = new TextImageObj(getName(files[i]), icon);
            item[1] = getExtension(files[i]);
            item[2] = getSize(files[i]);
            item[3] = getDate(files[i]);
            item[4] = "";
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
        if(f.getParent()!= null)
        {
            Object[] emptyRow = {TextImageObj.createEmptyObj(),"","","",""};           
            rs.add(emptyRow);
        }
        int i = 0;
        while(iterDir.hasNext())
            rs.add(iterDir.next());
        
        while(iterFile.hasNext())
            rs.add(iterFile.next());
        return rs;

    }

    public static String getName(File f) {
        String name = f.getName();
        if(!f.isDirectory())
        {
            int e = name.lastIndexOf(".");
            if(e > 0)
                name = name.substring(0,e);
        }
        return name;
    }
    public static String getExtension(File f)
    {
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

    public static String getSize(File f) {

        String rs = "<DIR>";
        if(!f.isDirectory())
            rs = "" + f.length();
        return rs;
    }

    public static String getDate(File f){
        long d = f.lastModified();
        Date datetime = new Date(d);
        TimeZone zone = TimeZone.getTimeZone("GMT+07:00");
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(zone);
        cal.setTime(datetime);
        return getDate(cal);
    }

    public static String getDate(Calendar cal)
    {
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
}
