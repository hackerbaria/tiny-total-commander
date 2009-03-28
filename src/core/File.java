/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class File extends Item{

    public File(String path){
        super(path);
    }

    @Override
    public void add(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void execute() throws IOException{
        // ref: http://tinyurl.com/executefile
        Runtime runner = Runtime.getRuntime();
        runner.exec("cmd /c start " + _Path);
    }

    @Override
    public void delete() {
        _Item.delete();

        // TODO: check for file protection + deleted succeed or failed
    }

    @Override
    public void copy(String anotherLocation) throws IOException{
        // create a new file
        new File(anotherLocation).create();
        
        // read content from the old file and write to the new one
        FileInputStream inStream = new FileInputStream(_Path);
        FileOutputStream outStream = new FileOutputStream(anotherLocation);

        int offset = 0;
        int len = 1024;
        byte[] buffer = new byte[len];
        int numByteRead = 0;

        while((numByteRead = inStream.read(buffer, offset, len)) != -1) {
            outStream.write(buffer, offset, numByteRead);
        }

        inStream.close();
        outStream.close();
    }

    @Override
    public void move(String newPath) throws IOException {
        this.copy(newPath);
        this.delete();

    }

    @Override
    public Boolean hasChild() {
        return false;
    }

    @Override
    public void create() {
        try {
            _Item.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public String getContent() throws IOException{
        StringBuilder builder = new StringBuilder();
        
        FileInputStream stream = new FileInputStream(_Path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        int len = 1024;
        int offset = 0;
        char[] buffer = new char[len];
        int numCharRead = 0;

        do{
            numCharRead = reader.read(buffer, offset, len);
            builder.append(buffer);
        }while(numCharRead >= len);
        

        return builder.toString();
    }

    @Override
    public void zip() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void unzip() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
