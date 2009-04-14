/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import ExtendComponent.TextImageObj;
import java.io.IOException;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


/**
 *
 * @author pmchanh
 * @source http://www.kodejava.org/examples/357.html
 */
public class FtpResource {
    private String _url;
    private String _password;
    private String _username;
    private FTPClient _ftpClient;


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
    public Boolean connect()
    {
        try {
            _ftpClient = new FTPClient();
            _ftpClient.connect(_url);
            _ftpClient.login(_username, _password);
            return true;
        } catch (SocketException ex) {
            Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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
    public void changeDir(String remotePath){
        if(_ftpClient != null){
            try {
                _ftpClient.cwd(remotePath);
            } catch (IOException ex) {
                Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

                FTPFile[] ftpFiles = _ftpClient.listFiles();
                for(FTPFile file:ftpFiles)
                {
                    Object[] item = new Object[5];
                    item[0] = new TextImageObj(file.getName(), null);// filename
                    item[1] = "";// extension
                    item[2] = file.getSize(); // file size
                    item[3] = FileResource.getDate(file.getTimestamp());
                    item[4] = ""; // attribute
                    rs.add(item);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(FtpResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public static void main(String[] args)
    {
        FtpResource ftp = new FtpResource("binhchanh.org", "bin76467", "binhchanh.org");
        ftp.connect();
        Vector v = ftp.getAllFiles("wwwroot/");
        ftp.disConnect();
    }

}
