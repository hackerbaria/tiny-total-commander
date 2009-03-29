/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


/**
 *
 * @author pmchanh
 */
public class FileResource {
    public static Object[] listFile(String pathname)
    {
        File f = new File(pathname);
        File[] files = f.listFiles();
        int n = files.length;        
        ArrayList<Object[]> dirArr = new ArrayList<Object[]>();
        ArrayList<Object[]> firArr = new ArrayList<Object[]>();
        for(int i = 0; i < n; i++ )
        {
            Object[] item = new Object[5];
            item[0] = getName(files[i]);
            item[1] = getExtension(files[i]);
            item[2] = getSize(files[i]);
            item[3] = getDate(files[i]);
            item[4] = "";
            if(files[i].isDirectory())
                dirArr.add(item);
            else
                firArr.add(item);            
        }
        Collections.sort(dirArr, new Comparer()) ;
        Collections.sort(firArr, new Comparer());
        Iterator iterDir = dirArr.iterator();
        Iterator iterFile = firArr.iterator();
        Object[] rs = new Object[n];
        int i = 0;
        while(iterDir.hasNext())
            rs[i++] = iterDir.next();
        
        while(iterFile.hasNext())
            rs[i++] = iterFile.next();
            
        
        return rs;

    }
    public static String getName(File f)
    {
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
    public static String getSize(File f)
    {

        String rs = "<DIR>";
        if(!f.isDirectory())
            rs = "" + f.length();
        return rs;
    }

    public static String getDate(File f)
    {
        long d = f.lastModified();
        Date datetime = new Date(d);
        TimeZone zone = TimeZone.getTimeZone("GMT+07:00");
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(zone);
        cal.setTime(datetime);
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
