/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.IOException;

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

    /**
     * create a new file
     * @param path
     * @return
     * @throws java.io.IOException
     */
    public static File create(String path) throws IOException {
        // create a real file
        java.io.File file = new java.io.File(path);
        file.createNewFile();

        return new File(path);
    }

    @Override
    public void execute() {
        //TODO: execute file
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void rename(String newName) {
        java.io.File file = new java.io.File(_Path);
        file.renameTo(new java.io.File(newName));
    }

    @Override
    public void delete() {
        java.io.File file = new java.io.File(_Path);
        file.delete();

        //TODO: check for file protection + deleted succeed or failed
    }

    @Override
    public void copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void move(String newPath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
