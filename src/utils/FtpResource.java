/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import ExtendComponent.TextImageObj;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import sun.awt.shell.ShellFolder;


/**
 *
 * @author pmchanh
 * @thamKhaoCodeFTP http://www.kodejava.org/examples/357.html
 * @cachLayIconFileTrenFtp http://blog.codebeach.com/2008/02/get-file-type-icon-with-java.html
 */
public class FtpResource {
    private String _url;
    private String _password;
    private String _username;
    private FTPClient _ftpClient;

    private String _rootPath;

    public String get_rootPath() {
        return _rootPath;
    }

   
    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

     public String get_workingDir() {
        try {
            return _ftpClient.printWorkingDirectory();
        } catch (IOException ex) {
            Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public FtpResource(String _url, char[] _password, String _username) {
        this._url = _url;
        this._password = new String(_password);
        this._username = _username;
    }
    public FtpResource(String _url, String _password, String _username) {
        this._url = _url;
        this._password = _password;
        this._username = _username;
    }
    /*
     *  connect to server
     */
    public Boolean connect() throws Exception
    {
        try {
            _ftpClient = new FTPClient();
            _ftpClient.connect(_url);
            _ftpClient.login(_username, _password);
            _rootPath = _ftpClient.printWorkingDirectory();
            return true;
        } catch (SocketException ex) {
            //Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        } catch (IOException ex) {
           // Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
           // return false;
             throw new Exception(ex.getMessage());  
        }

       //_ftpClient.login(_username, _password);
    }

    /*
     * disconnect from server
     */
    public void disConnect()
    {
        try {
            _ftpClient.logout();
            _ftpClient.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        _ftpClient = null;
    }
    
    /*
     *  change working directory
     */ 
    public Boolean changeDir(String remotePath){
        if(_ftpClient != null){
            try {
                _ftpClient.cwd(remotePath);
            } catch (IOException ex) {
                Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    /*
     * go to parent of working directory
     */
    public boolean goUp()
    {
        try {
             _ftpClient.changeToParentDirectory();
        } catch (IOException ex) {
            //Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }


    /*
     *  get all files from current dir in server
     */
    public Vector getAllFiles(String wd)
    {
        Vector rs = new Vector();
        if(_ftpClient != null && _ftpClient.isConnected())
        {
            try {
                changeDir(wd);
                ArrayList<Object[]> dirArr = new ArrayList<Object[]>();
                ArrayList<Object[]> firArr = new ArrayList<Object[]>();
                FTPFile[] ftpFiles = _ftpClient.listFiles();
                for(FTPFile file:ftpFiles)
                {
                    Object[] item = new Object[5];                     
                    Icon icon = getIcon(file);
                    
                    item[0] = new TextImageObj(getName(file), icon);// filename
                    item[1] = getExtension(file);
                    item[2] = getSize(file); // file size
                    item[3] = FileResource.getDate(file.getTimestamp());
                    item[4] = ""; // attribute
                    if(file.isDirectory())
                        dirArr.add(item);
                    else
                        firArr.add(item);
                }


                Collections.sort(dirArr, new Comparer());
                Collections.sort(firArr, new Comparer());
                Iterator iterDir = dirArr.iterator();
                Iterator iterFile = firArr.iterator();
                 if(!_ftpClient.printWorkingDirectory().equals(_rootPath))
                {
                    Object[] emptyRow = {TextImageObj.createEmptyObj(),"","","",""};
                    rs.add(emptyRow);
                }            
                while(iterDir.hasNext())
                    rs.add(iterDir.next());

                while(iterFile.hasNext())
                    rs.add(iterFile.next());
                
            } catch (IOException ex) {
                Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        return rs;
    }

    /*
     * Lay icon file
     * Thamkhao: http://blog.codebeach.com/2008/02/get-file-type-icon-with-java.html
     */
    public Icon getIcon(FTPFile file)
    {
        File temp = null;
        Icon ico = null;
        if(file.isFile()){           
            try {
                temp = File.createTempFile("icon", "." + getExtension(file));
            } catch (IOException ex) {
                Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(file.isDirectory()){           
            temp = new File("C:\\tinitemp");
            temp.mkdir();               
        }

        ShellFolder sf;
        try {
            sf = ShellFolder.getShellFolder(temp);
            ico = new ImageIcon(sf.getIcon(true).getScaledInstance(19, 19, Image.SCALE_SMOOTH));
            temp.delete();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ico;
    }
    public static String getExtension(FTPFile f)
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

    public static String getSize(FTPFile f) {

        String rs = "<DIR>";
        if(!f.isDirectory())
            rs = "" + f.getSize();
        return rs;
    }

    public static String getName(FTPFile f) {
        String name = f.getName();
        if(!f.isDirectory())
        {
            int e = name.lastIndexOf(".");
            if(e > 0)
                name = name.substring(0,e);
        }
        return name;
    }
    public static void main(String[] args)
    {
       /* FtpResource ftp = new FtpResource("dsaf", "�df", "dsafs");
        ftp.connect();
        Vector v = ftp.getAllFiles("wwwroot/");
        ftp.disConnect();*/
    }

}
